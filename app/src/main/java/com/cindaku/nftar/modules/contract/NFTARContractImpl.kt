package com.cindaku.nftar.modules.contract

import android.util.Log
import com.cindaku.nftar.CONTRACT_NAME
import com.cindaku.nftar.model.NFTSupplyForOwnerParams
import com.cindaku.nftar.model.NFTToken
import com.cindaku.nftar.model.NFTTokenParams
import com.cindaku.nftar.model.NFTTokensParams
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knear.android.provider.response.functioncall.FunctionCallResponse
import com.knear.android.service.NearMainService
import javax.inject.Singleton

@Singleton
class NFTARContractImpl constructor(
    private val nearMainService: NearMainService
) : NFTARContract {
    override fun token(token_id: String): NFTToken? {
        val response: FunctionCallResponse = nearMainService.callViewFunction(CONTRACT_NAME,"nft_token", Gson().toJson(NFTTokenParams(token_id)))
        val tokenString=response.result.result?.run {
            val rawResult = String(this)
            Log.d("NFT Token Received", rawResult)
            rawResult
        }
        if (tokenString.isNullOrEmpty() || tokenString=="null"){
            return null
        }
        return Gson().fromJson(tokenString, NFTToken::class.java)
    }

    override fun supply(account_id: String): Int {
        val response: FunctionCallResponse = nearMainService.callViewFunction(CONTRACT_NAME,"nft_supply_for_owner", Gson().toJson(NFTSupplyForOwnerParams(account_id)))
        val rawResponse=response.result.result?.run {
            val rawResult = String(this)
            Log.d("NFT Supply", rawResult)
            rawResult
        }
        return Gson().fromJson(rawResponse, Int::class.java)
    }

    override fun tokens(account_id: String, skip: Int, limit: Int): ArrayList<NFTToken> {
        val response: FunctionCallResponse = nearMainService.callViewFunction(CONTRACT_NAME,"nft_tokens_for_owner", Gson().toJson(
            NFTTokensParams(account_id, skip.toString(), limit)
        ))
        val responseString=response.result.result?.run {
            val rawResult = String(this)
            Log.d("NFT Tokens Received", rawResult)
            rawResult
        }
        if(responseString.isNullOrEmpty() || responseString=="null"){
            return arrayListOf()
        }
        return Gson().fromJson(responseString, object : TypeToken<ArrayList<NFTToken>>(){}.type)
    }

}