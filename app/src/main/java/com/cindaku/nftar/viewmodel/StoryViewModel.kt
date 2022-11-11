package com.cindaku.nftar.viewmodel

import androidx.lifecycle.ViewModel
import com.cindaku.nftar.modules.repository.UserRepository

class StoryViewModel(
    private val userRepository: UserRepository,
): ViewModel() {
}