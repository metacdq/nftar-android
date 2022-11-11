package com.cindaku.nftar.modules

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
    fun provideProfileFragment(mainMenuView: MainMenuView, nearMainService: NearMainService, nftarContract: NFTARContract): ProfileFragment{
        return ProfileFragment(mainMenuView, nearMainService, nftarContract)
    }
    @Provides
    fun provideHomeFragment(picasso: Picasso, userRepository: UserRepository, nearMainService: NearMainService, nftarContract: NFTARContract): StoryFragment{
        return StoryFragment(picasso, userRepository, nearMainService, nftarContract)
    }
}