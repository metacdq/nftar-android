package com.cindaku.nftar.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.enum.ChainType

@Dao
interface NFTDao {
    @Query("Select * from nft where token_id=:tokenId and contract_id=:contractId")
    fun getNFTByTokenId(tokenId: String, contractId: Int): NFT?
    @Query("Select * from nft where nft_id=:nftId")
    fun getNFTById(nftId: Int): NFT?
    @Query("Select * from nft where account_id=:accountId")
    fun getNFTs(accountId: Int): LiveData<List<NFT>>
    @Insert
    fun add(nft: NFT)
    @Update
    fun update(nft: NFT)
    @Query("Select * from nft where last_sync<:timeInSec")
    fun deleteLessThanTime(timeInSec: Int): Int
}