package com.cindaku.nftar.modules

import androidx.appcompat.app.AppCompatActivity
import com.cindaku.nftar.activity.MainActivity
import com.cindaku.nftar.view.MainMenuView
import dagger.Module
import dagger.Provides

@Module
class ActivityModule  {
    @Provides
    fun provideMainMenuView(activity: AppCompatActivity): MainMenuView{
        return activity as MainActivity
    }
}