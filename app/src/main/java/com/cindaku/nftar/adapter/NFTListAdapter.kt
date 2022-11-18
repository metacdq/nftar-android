package com.cindaku.nftar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cindaku.nftar.CONTRACT_URI
import com.cindaku.nftar.R
import com.cindaku.nftar.db.entity.NFT
import com.cindaku.nftar.view.StoryView
import com.cindaku.nftar.view.holder.NFTItemHolder
import com.squareup.picasso.Picasso

class NFTListAdapter(private val context: Context,
                     private val picasso: Picasso,
                     private val view: StoryView,
                     private var data: List<NFT> = arrayListOf()): RecyclerView.Adapter<NFTItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NFTItemHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_nft, parent, false)
        return NFTItemHolder(view)
    }

    override fun onBindViewHolder(holder: NFTItemHolder, position: Int) {
        val url=CONTRACT_URI+data[position].media
        picasso.load(url)
            .into(holder.nftImageView)
        holder.titleTextView.text = data[position].title
        holder.descTextView.text = data[position].description
        holder.nftImageView.setOnClickListener {
            view.onClick(data[position])
        }
        holder.downloadImageView.isVisible=data[position].downloaded==0
        holder.downloadImageView.setOnClickListener {
            if(!data[position].isDownloading){
                view.onDownloadRequest(data[position])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<NFT>){
        data= newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}