package com.cindaku.nftar.modules.repository

import android.util.Log
import com.cindaku.nftar.CONTRACT_NAME
import com.cindaku.nftar.db.dao.AccountDao
import com.cindaku.nftar.db.dao.ContractDao
import com.cindaku.nftar.db.dao.NFTDao
import com.cindaku.nftar.db.entity.Account
import com.cindaku.nftar.db.entity.Contract
import com.cindaku.nftar.enum.ChainType
import com.cindaku.nftar.model.NFTToken
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl constructor(
    private val accountDao: AccountDao,
    private val contractDao: ContractDao,
    private val nftDao: NFTDao,
): UserRepository {
    override fun save(accountId: String, token: NFTToken) {
        try {

        }catch (e: java.lang.Exception){
            Log.e("Save NFT", e.message.toString())
        }
    }

    override fun init(accountId: String) {
        try {
            val account=accountDao.findAccountById(accountId,ChainType.NEAR)
            if(account==null){
                accountDao.add(Account(
                    null,
                    accountId,
                    ChainType.NEAR
                ))
            }
            val contract=contractDao.findContractById(CONTRACT_NAME, ChainType.NEAR)
            if(contract==null){
                contractDao.add(Contract(
                    null,
                    ChainType.NEAR,
                    CONTRACT_NAME,
                    ""
                ))
            }
        }catch (e: java.lang.Exception){
            Log.e("Save Account", e.message.toString())
        }
    }

}