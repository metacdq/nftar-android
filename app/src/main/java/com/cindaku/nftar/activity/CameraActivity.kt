package com.cindaku.nftar.activity


import ai.deepar.ar.ARErrorType
import ai.deepar.ar.AREventListener
import ai.deepar.ar.CameraResolutionPreset
import ai.deepar.ar.DeepARImageFormat
import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.cindaku.nftar.NFTARApp
import com.cindaku.nftar.R
import com.cindaku.nftar.component.ARActivityComponent
import com.cindaku.nftar.viewmodel.CameraViewModel
import com.google.common.util.concurrent.ListenableFuture
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class CameraActivity : AREventListener, SurfaceHolder.Callback, AppCompatActivity() {
    lateinit var arActivityComponent: ARActivityComponent
    @Inject
    lateinit var cameraViewModel: CameraViewModel
    private lateinit var recordImageView: ImageView
    private lateinit var switchImageView: ImageView
    private lateinit var mainSurfaceView: SurfaceView
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
    private val defaultLensFacing = CameraSelector.LENS_FACING_FRONT
    private var lensFacing = defaultLensFacing
    private var buffers: Array<ByteBuffer?> = arrayOf()
    private var currentBuffer = 0
    private var buffersInitialized = false
    private val NUMBER_OF_BUFFERS = 2
    private val useExternalCameraTexture = false

    private var width = 0
    private var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        arActivityComponent=(application as NFTARApp).appComponent.arActivity().create(this)
        arActivityComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        recordImageView=findViewById(R.id.recordImageView)
        switchImageView=findViewById(R.id.switchImageView)
        mainSurfaceView=findViewById(R.id.mainSurfaceView)
        switchImageView.setOnClickListener {
            lensFacing = if (lensFacing == CameraSelector.LENS_FACING_FRONT) CameraSelector.LENS_FACING_BACK else CameraSelector.LENS_FACING_FRONT

            var cameraProvider: ProcessCameraProvider?
            try {
                cameraProvider = cameraProviderFuture!!.get()
                cameraProvider.unbindAll()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            initialize()
        }
    }


    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
                ),
                1
            )
        } else {
            // Permission has already been granted
            initialize()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty()) {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return  // no permission
                }
            }
            initialize()
        }
    }

    private fun initialize() {
        mainSurfaceView.holder.addCallback(this)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture!!.addListener({
            try {
                val cameraProvider = cameraProviderFuture!!.get()
                bindImageAnalysis(cameraProvider)
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindImageAnalysis(cameraProvider: ProcessCameraProvider) {
        val cameraResolutionPreset = CameraResolutionPreset.P1920x1080
        val orientation: Int = getScreenOrientation()
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE || orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            width = cameraResolutionPreset.width
            height = cameraResolutionPreset.height
        } else {
            width = cameraResolutionPreset.height
            height = cameraResolutionPreset.width
        }
        val cameraResolution = Size(width, height)
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        if (useExternalCameraTexture) {
            val preview = Preview.Builder()
                .setTargetResolution(cameraResolution)
                .build()
            cameraProvider.unbindAll()
            preview.setSurfaceProvider(cameraViewModel.getSurfaceProvider())
            cameraViewModel.getSurfaceProvider().setMirror(lensFacing == CameraSelector.LENS_FACING_FRONT)
            cameraProvider.bindToLifecycle(this, cameraSelector, preview)
        } else {
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(cameraResolution)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), imageAnalyzer)
            buffersInitialized = false
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle((this as LifecycleOwner), cameraSelector, imageAnalysis)
        }
    }

    private fun initializeBuffers(size: Int) {
        this.buffers = arrayOf(null, null)
        for (i in 0 until NUMBER_OF_BUFFERS) {
            this.buffers[i] = ByteBuffer.allocateDirect(size)
            this.buffers[i]?.order(ByteOrder.nativeOrder())
            this.buffers[i]?.position(0)
        }
    }

    private val imageAnalyzer = ImageAnalysis.Analyzer { image ->
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer
        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()
        if (!buffersInitialized) {
            buffersInitialized = true
            initializeBuffers(ySize + uSize + vSize)
        }
        val byteData = ByteArray(ySize + uSize + vSize)

        //U and V are swapped
        yBuffer[byteData, 0, ySize]
        vBuffer[byteData, ySize, vSize]
        uBuffer[byteData, ySize + vSize, uSize]
        buffers[currentBuffer]?.put(byteData)
        buffers[currentBuffer]?.position(0)
        cameraViewModel.getDeepAR().receiveFrame(
            buffers[currentBuffer],
            image.width, image.height,
            image.imageInfo.rotationDegrees,
            lensFacing == CameraSelector.LENS_FACING_FRONT,
            DeepARImageFormat.YUV_420_888,
            image.planes[1].pixelStride
        )
        currentBuffer = (currentBuffer + 1) % NUMBER_OF_BUFFERS
        image.close()
    }

    private fun getScreenOrientation(): Int {
        val rotation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display!!.rotation
        } else {
            windowManager.defaultDisplay.rotation
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            width=windowManager.currentWindowMetrics.bounds.width()
            height=windowManager.currentWindowMetrics.bounds.height()
        }else{
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            width = dm.widthPixels
            height = dm.heightPixels
        }
        // if the device's natural orientation is portrait:
        val orientation: Int = if ((rotation == Surface.ROTATION_0
                    || rotation == Surface.ROTATION_180) && height > width ||
            (rotation == Surface.ROTATION_90
                    || rotation == Surface.ROTATION_270) && width > height
        ) {
            when (rotation) {
                Surface.ROTATION_0 -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                Surface.ROTATION_90 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                Surface.ROTATION_180 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                Surface.ROTATION_270 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                else -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        } else {
            when (rotation) {
                Surface.ROTATION_0 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                Surface.ROTATION_90 -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                Surface.ROTATION_180 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                Surface.ROTATION_270 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                else -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
        return orientation
    }

    override fun onDestroy() {
        super.onDestroy()
        unsetAll()
    }

    override fun onStop() {
        super.onStop()
        unsetAll()
    }

    private fun unsetAll(){
        mainSurfaceView.holder.removeCallback(this)
        cameraViewModel.clean()
        var cameraProvider: ProcessCameraProvider? = null
        try {
            cameraProvider = cameraProviderFuture!!.get()
            cameraProvider.unbindAll()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
    override fun onBackPressed() {
        unsetAll()
        finish()
    }

    override fun screenshotTaken(p0: Bitmap?) {

    }

    override fun videoRecordingStarted() {

    }

    override fun videoRecordingFinished() {

    }

    override fun videoRecordingFailed() {

    }

    override fun videoRecordingPrepared() {

    }

    override fun shutdownFinished() {

    }

    override fun initialized() {
        mainSurfaceView.visibility = View.GONE
        mainSurfaceView.visibility = View.VISIBLE
        cameraViewModel.switchEffect("file:///android_asset/mask2.deepar")
    }

    override fun faceVisibilityChanged(p0: Boolean) {

    }

    override fun imageVisibilityChanged(p0: String?, p1: Boolean) {

    }

    override fun frameAvailable(p0: Image?) {

    }

    override fun error(p0: ARErrorType?, p1: String?) {

    }

    override fun effectSwitched(p0: String?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        cameraViewModel.onSurfaceChanged(holder, width, height)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        cameraViewModel.onSurfaceDestroyed()
    }
}