package com.cindaku.nftar.modules

import android.content.Context
import com.cindaku.nftar.APP_NAME
import com.cindaku.nftar.NETWORK_ID
import com.cindaku.nftar.RPC_ENDPOINT
import com.cindaku.nftar.WALLET_URL
import com.cindaku.nftar.modules.contract.NFTARContract
import com.cindaku.nftar.modules.contract.NFTARContractImpl
import com.knear.android.service.NearMainService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NearServiceModule {
    @Singleton
    @Provides
    fun provideNearService(context: Context): NearMainService{
        return NearMainService(context,
            networkId = NETWORK_ID,
            appName = APP_NAME,
            walletUrl = WALLET_URL,
            rcpEndpoint = RPC_ENDPOINT,
            fullAccess = false)
    }
    @Provides
    fun provideContract(nearMainService: NearMainService): NFTARContract{
        return NFTARContractImpl(nearMainService)
    }
}