package com.cindaku.nftar.viewmodel

import androidx.lifecycle.ViewModel
import com.cindaku.nftar.view.MainMenuView
import com.knear.android.service.NearMainService

class ProfileViewModel(
    private val mainMenuView: MainMenuView,
    private val nearMainService: NearMainService,
) : ViewModel(){
    fun getUsername(): String{
        return nearMainService.getAccountId().orEmpty()
    }
    fun logout(){
        nearMainService.logout()
        mainMenuView.showLogin()
    }
}