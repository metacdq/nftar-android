package com.cindaku.nftar.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class NFTWithContract(
    @Embedded var NFT: NFT,
    @Relation(parentColumn = "contract_id", entityColumn = "contract_id") var contract: Contract
)
