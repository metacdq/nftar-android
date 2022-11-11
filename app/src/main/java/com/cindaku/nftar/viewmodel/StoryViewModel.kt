package com.cindaku.nftar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cindaku.nftar.modules.contract.NFTARContract
import com.cindaku.nftar.modules.repository.UserRepository
import com.knear.android.service.NearMainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryViewModel(
    private val userRepository: UserRepository,
    private val nearMainService: NearMainService,
    private val nftarContract: NFTARContract
): ViewModel() {
    fun getNFT(){
        viewModelScope.launch(Dispatchers.IO) {
            nearMainService.getAccountId()?.let {
                var fetch = true
                var page = 0
                while (fetch){
                    val nfts=nftarContract.tokens(it, page * 10, 10)
                    for(nft in nfts){
                        userRepository.save(it, nft)
                    }
                    if(nfts.size < 10){
                        fetch=false
                    }
                    page += 1
                }
            }
        }
    }
}