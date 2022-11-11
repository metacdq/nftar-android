package com.cindaku.nftar.modules

import androidx.work.WorkManager
import com.cindaku.nftar.fragment.ProfileFragment
import com.cindaku.nftar.fragment.StoryFragment
import com.cindaku.nftar.modules.contract.NFTARContract
import com.cindaku.nftar.modules.repository.UserRepository
import com.cindaku.nftar.view.MainMenuView
import com.knear.android.service.NearMainService
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {
    @Provides
    fun provideProfileFragment(): ProfileFragment{
        return ProfileFragment()
    }
    @Provides
    fun provideHomeFragment(): StoryFragment{
        return StoryFragment()
    }
}