package com.cindaku.nftar.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cindaku.nftar.modules.repository.UserRepository
import com.cindaku.nftar.modules.storage.Storage
import com.cindaku.nftar.view.MainMenuView
import com.knear.android.service.NearMainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val storage: Storage,
    private val view: MainMenuView,
    private val nearMainService: NearMainService
) : ViewModel(){
    fun init(){
        if(!nearMainService.checkLogin()){
            view.showLogin()
        }else{
            viewModelScope.launch(Dispatchers.IO){
                nearMainService.getAccountId()?.let {
                    userRepository.init(it)
                }
            }
        }
    }

    fun attemptLogin(data: Uri){
        Log.d("LOGIN", data.toString())
        if(nearMainService.attemptLogin(data)){
            viewModelScope.launch(Dispatchers.IO){
                nearMainService.getAccountId()?.let {
                    userRepository.init(it)
                }
            }
            view.reload()
        }else{
            view.handleMessage("Login Failed")
        }
    }
}