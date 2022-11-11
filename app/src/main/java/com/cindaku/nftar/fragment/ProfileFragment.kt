package com.cindaku.nftar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cindaku.nftar.R
import com.cindaku.nftar.modules.contract.NFTARContract
import com.cindaku.nftar.view.MainMenuView
import com.cindaku.nftar.viewmodel.ProfileViewModel
import com.knear.android.service.NearMainService

class ProfileFragment (mainMenuView: MainMenuView, nearMainService: NearMainService, nftarContract: NFTARContract) : Fragment() {
    private lateinit var logoutButton: Button
    private lateinit var usernameTextView: TextView
    private val profileViewModel: ProfileViewModel=ProfileViewModel(mainMenuView, nearMainService, nftarContract)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton=view.findViewById(R.id.logoutButton)
        usernameTextView=view.findViewById(R.id.usernameTextview)
        usernameTextView.text=profileViewModel.getUsername()
        logoutButton.setOnClickListener {
            profileViewModel.logout()
        }
        profileViewModel.getNFT()
    }
}