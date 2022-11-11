package com.cindaku.nftar.viewmodel

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.cindaku.nftar.view.MainMenuView
import com.knear.android.OnReceiveUri
import com.knear.android.service.NearMainService
import javax.inject.Inject

class LoginViewModel: ViewModel() {
    @Inject
    lateinit var mainMenuView: MainMenuView
    @Inject
    lateinit var nearMainService: NearMainService
    fun processLogin(activity: AppCompatActivity){
        nearMainService.login(activity, object : OnReceiveUri{
            override fun onReceive(uri: Uri) {
                mainMenuView.handleFinishLogin(uri)
            }
        })
    }
}