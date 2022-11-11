package com.cindaku.nftar.modules.repository

import androidx.lifecycle.LiveData
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.model.NFTToken

interface UserRepository{
    fun save(accountId: String, token: NFTToken)
    fun init(accountId: String)
    fun findNFTById(nftId: Int): NFT?
    fun updateNFT(nft: NFT)
    fun deleteOld()
    fun getNFT(accountId: String): LiveData<List<NFT>>
}