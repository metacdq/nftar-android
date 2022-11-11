package com.cindaku.nftar.modules

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkManagerModule {
    @Provides
    @Singleton
    fun provideWorkManager(context: Context): WorkManager{
        return WorkManager.getInstance(context)
    }
}