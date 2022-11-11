package com.cindaku.nftar.modules.repository

import com.cindaku.nftar.db.dao.AccountDao
import com.cindaku.nftar.db.dao.ContractDao
import com.cindaku.nftar.db.dao.NFTDao
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class UserModule {
    @Provides
    fun provideUserRepository(accountDao: AccountDao, nftDao: NFTDao, contractDao: ContractDao): UserRepository{
        return UserRepositoryImpl(accountDao, contractDao, nftDao)
    }
}