package com.cindaku.nftar.modules.repository

import com.cindaku.nftar.db.dao.AccountDao
import com.cindaku.nftar.db.dao.ContractDao
import com.cindaku.nftar.db.dao.NFTDao
import com.cindaku.nftar.model.NFTToken
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl constructor(
    private val accountDao: AccountDao,
    private val contractDao: ContractDao,
    private val nftDao: NFTDao,
): UserRepository {
    override fun save(accountId: String, token: NFTToken) {

    }

}