package com.cindaku.nftar.modules.contract

import com.cindaku.nftar.model.NFTToken

interface NFTARContract {
    fun token(token_id: String): NFTToken?
    fun supply(account_id: String): Int
    fun tokens(account_id: String, skip: Int, limit: Int): ArrayList<NFTToken>
}