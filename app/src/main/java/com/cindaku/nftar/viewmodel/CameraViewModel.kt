package com.cindaku.nftar.viewmodel

import ai.deepar.ar.DeepAR
import android.view.SurfaceHolder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cindaku.nftar.provider.ARSurfaceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    private val deepAR: DeepAR,
    private val arSurfaceProvider: ARSurfaceProvider
) : ViewModel(){
    private val currentMask = 0
    private val currentEffect = 0
    private val currentFilter = 0
    private val screenOrientation = 0
    private var effects: ArrayList<String> = arrayListOf()

    private var recording = false
    private var currentSwitchRecording = false

    fun onSurfaceDestroyed(){
        deepAR.setRenderSurface(null, 0, 0)
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