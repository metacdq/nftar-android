package com.cindaku.nftar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.enum.ChainType

@Dao
interface NFTDao {
    @Query("Select * from nft where token_id=:tokenId and contract_id=:contractId and chain=:chainType")
    fun getNFTById(tokenId: String, contractId: String, chainType: ChainType): NFT
    @Insert
    fun add(nft: NFT)
    @Update
    fun update(nft: NFT)
}