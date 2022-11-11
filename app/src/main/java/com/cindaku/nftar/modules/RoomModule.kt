package com.cindaku.nftar.modules

import android.content.Context
import androidx.room.Room
import com.cindaku.nftar.APP_NAME
import com.cindaku.nftar.db.NFTARDatabase
import com.cindaku.nftar.db.dao.AccountDao
import com.cindaku.nftar.db.dao.ContractDao
import com.cindaku.nftar.db.dao.NFTDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): NFTARDatabase{
        return Room.databaseBuilder(context,NFTARDatabase::class.java, APP_NAME).build()
    }
    @Provides
    fun provideAccountDao(database: NFTARDatabase): AccountDao{
        return  database.accountDao()
    }
    @Provides
    fun  provideNFTDao(database: NFTARDatabase): NFTDao{
        return  database.nftDao()
    }
    @Provides
    fun provideContractDao(database: NFTARDatabase): ContractDao{
        return database.contractDao()
    }
}