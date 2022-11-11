package com.cindaku.nftar.modules.storage

import android.content.Context
import com.cindaku.nftar.APP_NAME
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyValueStorage @Inject constructor(context: Context): Storage {
    private val sharedPreferences=context.getSharedPreferences(APP_NAME,Context.MODE_PRIVATE)
    override fun get(key: String): String {
       return sharedPreferences.getString(key,null).orEmpty()
    }

    override fun set(key: String, value: String) {
        with(sharedPreferences.edit()){
            putString(key,value)
            apply()
        }
    }
}