package com.cindaku.nftar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cindaku.nftar.modules.contract.NFTARContract
import com.cindaku.nftar.view.MainMenuView
import com.knear.android.service.NearMainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val mainMenuView: MainMenuView,
    private val nearMainService: NearMainService,
    private val nftarContract: NFTARContract
) : ViewModel(){
    fun getNFT(){
        viewModelScope.launch(Dispatchers.IO) {
            nearMainService.getAccountId()?.let {
                nftarContract.token("1")
                nftarContract.tokens(it, 0, 10)
                nftarContract.supply(it)
            }
        }
    }
    fun getUsername(): String{
        return nearMainService.getAccountId().orEmpty()
    }
    fun logout(){
        nearMainService.logout()
        mainMenuView.showLogin()
    }
}