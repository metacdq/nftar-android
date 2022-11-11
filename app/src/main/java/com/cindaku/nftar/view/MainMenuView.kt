package com.cindaku.nftar.view

import android.net.Uri

interface MainMenuView {
    fun showLogin()
    fun hideLogin()
    fun hideLoading()
    fun showLoading()
    fun handleMessage(message: String)
    fun handleFinishLogin(data: Uri)
    fun reload()
}