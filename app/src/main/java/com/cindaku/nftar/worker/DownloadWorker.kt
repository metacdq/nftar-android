package com.cindaku.nftar.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cindaku.nftar.CONTRACT_URI
import com.cindaku.nftar.NFTARApp
import com.cindaku.nftar.model.NFTMetadata
import com.google.gson.Gson
import okhttp3.Request
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.io.FileOutputStream

class DownloadWorker(val context: Context, val workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        val tokenId=workerParams.inputData.getInt("ID",0)
        if(tokenId==0){
            return Result.failure()
        }

        val app = (context.applicationContext as NFTARApp).appComponent
        val nft = app.userRepository().findNFTById(tokenId) ?: return Result.success()
        try {
            nft.run {
                nft.isDownloading=true
                app.userRepository().updateNFT(nft)
                val requestJson= Request.Builder()
                    .url(CONTRACT_URI+nft.reference)
                    .build()
                val responseJson = app.http().newCall(requestJson).execute()
                if(responseJson.isSuccessful){
                    val metdaata=Gson().fromJson(responseJson.body!!.string(), NFTMetadata::class.java)
                    val requestModel= Request.Builder()
                        .url(CONTRACT_URI+metdaata.model!!)
                        .build()
                    val responseModel = app.http().newCall(requestModel).execute()
                    if(responseModel.isSuccessful){
                        try {
                            val shaHex =
                                String(Hex.encodeHex(DigestUtils.md5(CONTRACT_URI+metdaata.model!!)))
                            val file=app.fileStorage().newData(shaHex)
                            val fos = FileOutputStream(file);
                            fos.write(responseModel.body!!.bytes());
                            fos.flush();
                            fos.close()
                            nft.downloaded=1
                            nft.model=file.absolutePath
                            nft.isDownloading=false
                            app.userRepository().updateNFT(nft)
                            return Result.success()
                        }catch (e: Exception){
                            nft.isDownloading=false
                            app.userRepository().updateNFT(nft)
                            return Result.failure()
                        }
                    }
                    return Result.retry()
                }else if(responseJson.code!=404 && responseJson.code!=500){
                    nft.isDownloading=false
                    app.userRepository().updateNFT(nft)
                    return Result.retry()
                }else{
                    nft.isDownloading=false
                    app.userRepository().updateNFT(nft)
                    return Result.failure()
                }
            }
        }catch (e: Exception){
            Log.e("Worker",e.message.toString())
            return Result.failure()
        }
    }

}