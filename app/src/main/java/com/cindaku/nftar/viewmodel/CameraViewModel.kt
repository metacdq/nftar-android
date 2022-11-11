package com.cindaku.nftar.viewmodel

import ai.deepar.ar.DeepAR
import android.net.Uri
import android.util.Log
import android.view.SurfaceHolder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cindaku.nftar.modules.storage.FileStorage
import com.cindaku.nftar.provider.ARSurfaceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import javax.inject.Inject


class CameraViewModel @Inject constructor(
    private val deepAR: DeepAR,
    private val fileStorage: FileStorage,
    private val arSurfaceProvider: ARSurfaceProvider
) : ViewModel(){

    private var recording = false
    private var recordingFile: File? = null
    private var currentSwitchRecording = false

    fun onSurfaceDestroyed(){
        deepAR.setRenderSurface(null, 0, 0)
    }
    fun isRecording(): Boolean{
        return recording
    }
    fun startRecording(width: Int, height: Int){
        recording=true
        viewModelScope.launch(Dispatchers.IO) {
            recordingFile = fileStorage.newMovie("video_nftar_" + Date().time + ".mp4")
            Log.d("RECORD", recordingFile!!.path)
            deepAR.startVideoRecording(recordingFile!!.path, width / 2, height / 2)
        }
    }
    fun stopRecording(): File {
        recording=false
        viewModelScope.launch(Dispatchers.IO) {
            deepAR.stopVideoRecording()
        }
        return recordingFile!!
    }
    fun onSurfaceChanged(surfaceHolder: SurfaceHolder, width: Int, height: Int){
        deepAR.setRenderSurface(surfaceHolder.surface, width, height)
    }
    fun getSurfaceProvider(): ARSurfaceProvider{
        return arSurfaceProvider
    }
    fun switchEffect(effect: String){
        deepAR.switchEffect("effect", effect)
    }
    fun getDeepAR(): DeepAR{
        return deepAR
    }
    fun clean(){
        recording = false
        currentSwitchRecording = false
        arSurfaceProvider.stop()
        deepAR.release()
    }
}