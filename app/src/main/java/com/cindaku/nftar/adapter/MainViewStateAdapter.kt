package com.cindaku.nftar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cindaku.nftar.fragment.ProfileFragment
import com.cindaku.nftar.fragment.StoryFragment
import javax.inject.Inject

class MainViewStateAdapter @Inject constructor(fa: FragmentActivity,
                                               private val profileFragment: ProfileFragment, private val storyFragment: StoryFragment): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> {
                storyFragment
            }
            1-> {
                profileFragment
            }
            else -> {
                storyFragment
            }
        }
    }
}