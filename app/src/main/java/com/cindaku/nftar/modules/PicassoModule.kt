package com.cindaku.nftar.modules

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PicassoModule {
    @Provides
    @Singleton
    fun getPicasso(): Picasso{
        return Picasso.get()
    }
}