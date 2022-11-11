package com.cindaku.nftar.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cindaku.nftar.db.type.Converters
import com.cindaku.nftar.enum.ChainType

@Entity
data class NFT(
    @ColumnInfo(name = "nft_id") @PrimaryKey(autoGenerate = true) var NFTID: Int?,
    @ColumnInfo(name = "chain") @TypeConverters(Converters::class) var PROTOCOL: ChainType=ChainType.NEAR,
    @ColumnInfo(name = "account_id") var accountID: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "media") var media: String,
    @ColumnInfo(name = "reference") var reference: String,
    @ColumnInfo(name = "contract_id") var contract: String,
)
