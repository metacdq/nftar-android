package com.cindaku.nftar.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cindaku.nftar.db.type.Converters
import com.cindaku.nftar.enum.ChainType

@Entity
data class Contract(
    @ColumnInfo(name = "contract_id") @PrimaryKey(autoGenerate = true) var contractID: Int?,
    @ColumnInfo(name = "chain") @TypeConverters(Converters::class) var PROTOCOL: ChainType=ChainType.NEAR,
    @ColumnInfo(name = "contract_name") var contractName: Int,
    @ColumnInfo(name = "base_url") var baseURL: Int,
)
