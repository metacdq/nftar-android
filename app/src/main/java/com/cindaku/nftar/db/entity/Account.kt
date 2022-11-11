package com.cindaku.nftar.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cindaku.nftar.db.type.Converters
import com.cindaku.nftar.enum.ChainType

@Entity
data class Account (
    @ColumnInfo(name = "account_id") @PrimaryKey(autoGenerate = true) var AccountID: Int?,
    @ColumnInfo(name = "account_name") var accountName: String?,
)