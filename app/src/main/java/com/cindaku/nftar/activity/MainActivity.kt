package com.cindaku.nftar.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.cindaku.nftar.NFTARApp
import com.cindaku.nftar.R
import com.cindaku.nftar.adapter.MainViewStateAdapter
import com.cindaku.nftar.component.ActivityComponent
import com.cindaku.nftar.fragment.LoginFragment
import com.cindaku.nftar.fragment.ProfileFragment
import com.cindaku.nftar.fragment.StoryFragment
import com.cindaku.nftar.view.MainMenuView
import com.cindaku.nftar.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class MainActivity: AppCompatActivity(), MainMenuView {
    lateinit var activityComponent: ActivityComponent
    @Inject
    lateinit var mainViewModel: MainViewModel
    private lateinit var loginDialog: LoginFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var storyFragment: StoryFragment
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent=(application as NFTARApp).appComponent.activity().create(this)
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout=findViewById(R.id.mainTabLayout)
        viewPager=findViewById(R.id.mainViewPager)
    }

    override fun onStart() {
        super.onStart()
        loginDialog= LoginFragment()
        profileFragment=ProfileFragment()
        storyFragment=StoryFragment()
        viewPager.adapter=MainViewStateAdapter(this, profileFragment, storyFragment)
        viewPager.isUserInputEnabled=false
        mainViewModel.init()
        TabLayoutMediator(tabLayout,viewPager) { tab, position ->
            run {
                when (position) {
                    0 -> {
                        tab.icon = ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.ic_baseline_amp_stories_24
                        )
                    }
                    1 -> {
                        tab.icon = ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.ic_baseline_account_circle_24
                        )
                    }
                }
            }
        }.attach()
    }

    override fun showLogin() {
        loginDialog.isCancelable=false
        loginDialog.show(supportFragmentManager,"LOGIN")
    }

    override fun hideLogin() {
        loginDialog.dismiss()
    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    override fun handleMessage(message: String) {
        Toast.makeText(baseContext,message,Toast.LENGTH_SHORT).show()
    }

    override fun handleFinishLogin(data: Uri) {
        mainViewModel.attemptLogin(data)
    }

    override fun reload() {
        val i=Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

}