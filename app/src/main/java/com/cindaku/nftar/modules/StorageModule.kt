package com.cindaku.nftar.modules

import com.cindaku.nftar.modules.storage.KeyValueStorage
import com.cindaku.nftar.modules.storage.Storage
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class StorageModule {
    @Singleton
    @Binds
    abstract fun provideStorage(storage: KeyValueStorage): Storage
}