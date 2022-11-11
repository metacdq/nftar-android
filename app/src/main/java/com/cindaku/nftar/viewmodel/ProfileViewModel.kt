package com.cindaku.nftar.viewmodel

import androidx.lifecycle.ViewModel
import com.cindaku.nftar.view.MainMenuView
import com.knear.android.service.NearMainService
import javax.inject.Inject

class ProfileViewModel : ViewModel(){
    @Inject
    lateinit var mainMenuView: MainMenuView
    @Inject
    lateinit var nearMainService: NearMainService
    fun getUsername(): String{
        return nearMainService.getAccountId().orEmpty()
    }
    fun logout(){
        nearMainService.logout()
        mainMenuView.showLogin()
    }
}