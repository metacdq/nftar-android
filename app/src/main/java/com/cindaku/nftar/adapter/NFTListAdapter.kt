package com.cindaku.nftar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cindaku.nftar.R
import com.cindaku.nftar.RoundedTransformation
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.view.StoryView
import com.cindaku.nftar.view.holder.NFTItemHolder
import com.squareup.picasso.Picasso

class NFTListAdapter(private val context: Context,
                     private val picasso: Picasso,
                     private val view: StoryView,
                     private var data: ArrayList<NFT> = arrayListOf()): RecyclerView.Adapter<NFTItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NFTItemHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_nft, parent, false)
        return NFTItemHolder(view)
    }

    override fun onBindViewHolder(holder: NFTItemHolder, position: Int) {
        picasso.load("https://th.bing.com/th/id/OIP.DG4-RUF3xXNcYj2xnY_GIQHaGq?w=198&h=178&c=7&r=0&o=5&pid=1.7")
            .transform(RoundedTransformation(5 , 0))
            .into(holder.nftImageView)
        holder.nftImageView.setOnClickListener {
            view.onClick(null)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: ArrayList<NFT>){
        data=newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return 1
    }
}