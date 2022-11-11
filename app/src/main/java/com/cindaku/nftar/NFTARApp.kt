package com.cindaku.nftar

import android.app.Application
import com.cindaku.nftar.component.AppComponent
import com.cindaku.nftar.component.DaggerAppComponent

class NFTARApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(context = applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
    }
}