package com.cindaku.nftar.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.modules.contract.NFTARContract
import com.cindaku.nftar.modules.repository.UserRepository
import com.cindaku.nftar.worker.DownloadWorker
import com.knear.android.service.NearMainService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoryViewModel(): ViewModel() {
    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var nearMainService: NearMainService
    @Inject
    lateinit var nftarContract: NFTARContract
    @Inject
    lateinit var workManager: WorkManager
    fun fetchNFT(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
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
                    userRepository.deleteOld()
                }
            }catch (e: java.lang.Exception){
                Log.e("getNFT", e.message.toString())
            }
        }
    }
    fun download(nft: NFT){
        viewModelScope.launch(Dispatchers.IO){
            val data= Data.Builder()
            data.putInt("ID", nft.NFTID!!)
            val request=OneTimeWorkRequest.Builder(DownloadWorker::class.java)
                .setInputData(data.build()).build()
            workManager.enqueue(request)
        }
    }
    fun isLogin(): Boolean{
        return !nearMainService.getAccountId().isNullOrEmpty()
    }
    fun getNFT(lifecycleOwner: LifecycleOwner, observer: Observer<List<NFT>>){
        viewModelScope.launch(Dispatchers.IO){
            val liveData=userRepository.getNFT(nearMainService.getAccountId()!!)
            withContext(Dispatchers.Main){
                liveData.observe(lifecycleOwner,observer)
            }
        }
    }
}