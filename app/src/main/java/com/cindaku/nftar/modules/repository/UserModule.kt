package com.cindaku.nftar.modules.repository

import com.cindaku.nftar.db.dao.AccountDao
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class UserModule {
    @Provides
    fun provideUserRepository(accountDao: AccountDao): UserRepository{
        return UserRepositoryImpl(accountDao)
    }
}