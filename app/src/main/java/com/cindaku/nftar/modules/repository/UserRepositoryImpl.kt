package com.cindaku.nftar.modules.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.cindaku.nftar.CONTRACT_NAME
import com.cindaku.nftar.CONTRACT_URI
import com.cindaku.nftar.db.dao.AccountDao
import com.cindaku.nftar.db.dao.ContractDao
import com.cindaku.nftar.db.dao.NFTDao
import com.cindaku.nftar.db.entity.Account
import com.cindaku.nftar.db.entity.Contract
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.enum.ChainType
import com.cindaku.nftar.model.NFTToken
import java.util.Date
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl constructor(
    private val accountDao: AccountDao,
    private val contractDao: ContractDao,
    private val nftDao: NFTDao,
): UserRepository {
    override fun save(accountId: String, token: NFTToken) {
        try {
            val account=accountDao.findAccountByName(accountId)
            val contract=contractDao.findContractById(CONTRACT_NAME)
            if(contract!=null && account!=null){
                val nft=nftDao.getNFTByTokenId(token.token_id, contract.contractID!!)
                if(nft==null){
                    nftDao.add(NFT(
                        null,
                        token.token_id,
                        account.AccountID!!,
                        token.metadata.title!!,
                        token.metadata.description!!,
                        token.metadata.media!!,
                        token.metadata.reference!!,
                        contract.contractID!!
                    ))
                }else{
                    nft.last_sync= (Date().time/1000).toInt()
                    nftDao.update(nft)
                }
            }
        }catch (e: java.lang.Exception){
            Log.e("Save NFT", e.message.toString())
        }
    }

    override fun init(accountId: String) {
        try {
            val account=accountDao.findAccountByName(accountId)
            if(account==null){
                accountDao.add(Account(
                    null,
                    accountId,
                ))
            }
            val contract=contractDao.findContractById(CONTRACT_NAME)
            if(contract==null){
                contractDao.add(Contract(
                    null,
                    CONTRACT_NAME,
                    CONTRACT_URI
                ))
            }
        }catch (e: java.lang.Exception){
            Log.e("Save Account", e.message.toString())
        }
    }

    override fun findNFTById(nftId: Int): NFT? {
        return nftDao.getNFTById(nftId)
    }

    override fun updateNFT(nft: NFT) {
        nftDao.update(nft)
    }

    override fun deleteOld() {
        nftDao.deleteLessThanTime((Date().time/1000).toInt()-3600)
    }

    override fun getNFT(accountId: String): LiveData<List<NFT>> {
        val account = accountDao.findAccountByName(accountId)
        return nftDao.getNFTs(account!!.AccountID!!)
    }

}