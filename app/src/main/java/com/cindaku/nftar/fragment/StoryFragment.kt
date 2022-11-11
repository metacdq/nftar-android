package com.cindaku.nftar.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cindaku.nftar.R
import com.cindaku.nftar.activity.CameraActivity
import com.cindaku.nftar.activity.MainActivity
import com.cindaku.nftar.adapter.NFTListAdapter
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.view.StoryView
import com.cindaku.nftar.viewmodel.StoryViewModel

class StoryFragment() : StoryView,Fragment() {
    private lateinit var nftList: RecyclerView
    private lateinit var buyTextView: TextView
    private lateinit var adapter: NFTListAdapter
    private lateinit var storyViewModel: StoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storyViewModel = ViewModelProvider(requireActivity()).get(StoryViewModel::class.java)
        (requireActivity() as MainActivity).activityComponent.inject(storyViewModel)
        if(storyViewModel.isLogin()){
            storyViewModel.fetchNFT()
        }
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
        adapter= NFTListAdapter(requireContext(), storyViewModel.picasso, this)
        nftList.adapter=adapter
        buyTextView.setOnClickListener {
            val i=Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://nftar.cindaku.com")
            requireActivity().startActivity(i)
        }
        if(storyViewModel.isLogin()){
            storyViewModel.getNFT(viewLifecycleOwner
            ) { adapter.setData(it) }
        }
    }

    override fun onClick(nft: NFT?) {
        nft?.let {
            if(nft.downloaded==1){
                val intent= Intent(context, CameraActivity::class.java)
                intent.putExtra("effect", it.model)
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(),"You nedd to tap download first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDownloadRequest(nft: NFT?) {
        nft?.let {
            storyViewModel.download(it)
        }
    }
}