package com.cindaku.nftar.component

import android.app.Activity
import android.content.Context
import androidx.work.WorkManager
import com.cindaku.nftar.modules.*
import com.cindaku.nftar.modules.contract.NFTARContract
import com.cindaku.nftar.modules.repository.UserModule
import com.cindaku.nftar.modules.repository.UserRepository
import com.cindaku.nftar.modules.storage.FileStorage
import com.cindaku.nftar.modules.storage.Storage
import com.knear.android.service.NearMainService
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [HttpModules::class, RoomModule::class,StorageModule::class, UserModule::class, NearServiceModule::class, WorkManagerModule::class, PicassoModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun context(): Context
    fun activity(): ActivityComponent.Factory
    fun arActivity(): ARActivityComponent.Factory
    fun storage(): Storage
    fun fileStorage(): FileStorage
    fun userRepository(): UserRepository
    fun nearMainService(): NearMainService
    fun workManager(): WorkManager
    fun nftContract(): NFTARContract
    fun http(): OkHttpClient
    fun picasso(): Picasso
    fun inject(activity: Activity)
}