package com.cindaku.nftar.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cindaku.nftar.DB_VERSION
import com.cindaku.nftar.db.dao.AccountDao
import com.cindaku.nftar.db.dao.ContractDao
import com.cindaku.nftar.db.dao.NFTDao
import com.cindaku.nftar.db.entity.Account
import com.cindaku.nftar.db.entity.Contract
import com.cindaku.nftar.db.entity.NFT

@Database(entities = [Account::class,NFT::class,Contract::class], version = DB_VERSION, exportSchema = false)
abstract class NFTARDatabase: RoomDatabase() {
    abstract fun accountDao() : AccountDao
    abstract fun nftDao() : NFTDao
    abstract fun contractDao() : ContractDao
}