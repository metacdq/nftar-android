package com.cindaku.nftar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.cindaku.nftar.db.entity.Account

@Dao
interface AccountDao {
    @Insert
    fun add(account: Account)
}