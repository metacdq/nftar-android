package com.cindaku.nftar.modules.repository

import com.cindaku.nftar.model.NFTToken

interface UserRepository{
    fun save(accountId: String, token: NFTToken)
    fun init(accountId: String)
}