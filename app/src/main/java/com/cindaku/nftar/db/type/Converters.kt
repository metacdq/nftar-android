package com.cindaku.nftar.db.type

import androidx.room.TypeConverter
import com.cindaku.nftar.enum.ChainType

class Converters {
    @TypeConverter
    fun  toChainType(value: Int): ChainType = enumValues<ChainType>()[value]
    @TypeConverter
    fun fromCharType(value: ChainType): Int=value.ordinal
}