package com.cindaku.nftar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cindaku.nftar.db.entity.Account
import com.cindaku.nftar.enum.ChainType

@Dao
interface AccountDao {
    @Query("Select * from account where account_id=:accountId and chain=:chainType")
    fun findAccountById(accountId: String, chainType: ChainType): Account?
    @Insert
    fun add(account: Account)
    @Update
    fun update(account: Account)
}