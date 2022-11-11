package com.cindaku.nftar.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cindaku.nftar.R
import com.cindaku.nftar.activity.CameraActivity
import com.cindaku.nftar.adapter.NFTListAdapter
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.modules.repository.UserRepository
import com.cindaku.nftar.view.StoryView
import com.cindaku.nftar.viewmodel.StoryViewModel
import com.squareup.picasso.Picasso

class StoryFragment(private val picasso: Picasso, userRepository: UserRepository) : StoryView,Fragment() {
    private lateinit var nftList: RecyclerView
    private lateinit var buyTextView: TextView
    private lateinit var adapter: NFTListAdapter
    private val storyViewModel: StoryViewModel= StoryViewModel(userRepository)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter= NFTListAdapter(requireContext(), picasso, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nftList=view.findViewById(R.id.nftList)
        buyTextView=view.findViewById(R.id.buyTextView)
        nftList.layoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        nftList.adapter=adapter
        buyTextView.setOnClickListener {
            val i=Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://nftar.cindaku.com")
            requireActivity().startActivity(i)
        }
    }

    override fun onClick(nft: NFT?) {
        val intent= Intent(context, CameraActivity::class.java)
        startActivity(intent)
    }
}