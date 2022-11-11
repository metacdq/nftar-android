package com.cindaku.nftar.view.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cindaku.nftar.R

class NFTItemHolder(item: View): RecyclerView.ViewHolder(item) {
    var nftImageView: ImageView=item.findViewById(R.id.nftImageView)
    var downloadImageView: ImageView=item.findViewById(R.id.downloadImageView)
    var titleTextView: TextView=item.findViewById(R.id.titleTextView)
    var descTextView: TextView=item.findViewById(R.id.descTextView)
}