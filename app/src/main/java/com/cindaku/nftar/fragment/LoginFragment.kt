package com.cindaku.nftar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cindaku.nftar.R
import com.cindaku.nftar.activity.MainActivity
import com.cindaku.nftar.modules.repository.UserRepository
import com.cindaku.nftar.view.MainMenuView
import com.cindaku.nftar.viewmodel.LoginViewModel
import com.knear.android.service.NearMainService
import javax.inject.Inject

class LoginFragment  : DialogFragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.Theme_NFTARFull)
        loginViewModel=ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        (requireActivity() as MainActivity).activityComponent.inject(loginViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton = view.findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            loginViewModel.processLogin(activity as MainActivity)
        }
    }
}