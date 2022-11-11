package com.cindaku.nftar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cindaku.nftar.db.entity.Contract
import com.cindaku.nftar.enum.ChainType

@Dao
interface ContractDao {
    @Query("Select * from contract where contract_name=:contractName")
    fun findContractById(contractName: String): Contract?
    @Insert
    fun add(contract: Contract)
    @Update
    fun update(contract: Contract)
}