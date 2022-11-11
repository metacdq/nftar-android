package com.cindaku.nftar.modules.storage

interface Storage {
    fun get(key: String): String
    fun set(key: String,value: String)
}