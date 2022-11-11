package com.cindaku.nftar.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class HttpModules {
    @Singleton
    @Provides
    fun provideProvideHTTPClient(): OkHttpClient{
        return OkHttpClient()
    }
}