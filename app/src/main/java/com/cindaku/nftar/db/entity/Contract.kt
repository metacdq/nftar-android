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
    @ColumnInfo(name = "contract_name") var contractName: String,
    @ColumnInfo(name = "base_url") var baseURL: String,
)
