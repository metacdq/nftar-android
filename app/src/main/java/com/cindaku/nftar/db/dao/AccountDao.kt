package com.cindaku.nftar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cindaku.nftar.db.entity.Account

@Dao
interface AccountDao {
    @Query("Select * from account where account_name=:accountName")
    fun findAccountByName(accountName: String): Account?
    @Insert
    fun add(account: Account)
    @Update
    fun update(account: Account)
}