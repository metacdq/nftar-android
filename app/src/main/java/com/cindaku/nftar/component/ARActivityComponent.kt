package com.cindaku.nftar.component

import androidx.appcompat.app.AppCompatActivity
import com.cindaku.nftar.activity.CameraActivity
import com.cindaku.nftar.modules.DeepARModule
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [DeepARModule::class])
interface ARActivityComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance activity: AppCompatActivity): ARActivityComponent
    }
    fun inject(cameraActivity: CameraActivity)
}