package com.cindaku.nftar.modules

import ai.deepar.ar.AREventListener
import ai.deepar.ar.DeepAR
import androidx.appcompat.app.AppCompatActivity
import com.cindaku.nftar.activity.CameraActivity
import com.cindaku.nftar.provider.ARSurfaceProvider
import dagger.Module
import dagger.Provides

@Module
class DeepARModule {
    @Provides
    fun provideCameraActivity(activity: AppCompatActivity): CameraActivity {
        return activity as CameraActivity
    }
    @Provides
    fun provideDeepAR(activity: CameraActivity): DeepAR{
        val deepAR=DeepAR(activity)
        deepAR.setLicenseKey("281b4abc4b048eef30a1fe06f04b8053b34be02ef397989d535ce7be1899ed92d895d20a07bdae37")
        deepAR.initialize(activity.baseContext, activity as AREventListener)
        return deepAR
    }
    @Provides
    fun provideARSurfaceProvider(activity: CameraActivity, deepAR: DeepAR): ARSurfaceProvider{
        return ARSurfaceProvider(activity.baseContext, deepAR)
    }
}