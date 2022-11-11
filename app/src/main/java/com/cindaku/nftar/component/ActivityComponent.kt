package com.cindaku.nftar.component

import androidx.appcompat.app.AppCompatActivity
import com.cindaku.nftar.activity.MainActivity
import com.cindaku.nftar.modules.ActivityModule
import com.cindaku.nftar.view.MainMenuView
import com.cindaku.nftar.viewmodel.LoginViewModel
import com.cindaku.nftar.viewmodel.ProfileViewModel
import com.cindaku.nftar.viewmodel.StoryViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance activity: AppCompatActivity): ActivityComponent
    }
    fun mainMenuView(): MainMenuView
    fun inject(mainActivity: MainActivity)
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(storyViewModel: StoryViewModel)
    fun inject(loginViewModel: LoginViewModel)
}