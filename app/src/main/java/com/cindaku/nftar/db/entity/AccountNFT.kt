package com.cindaku.nftar.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AccountNFT(
    @Embedded var account: Account,
    @Relation(parentColumn = "account_id", entityColumn = "account_id") var listNFT: List<NFTWithContract>
)
