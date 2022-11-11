package com.cindaku.nftar.view.holder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cindaku.nftar.R

class NFTItemHolder(item: View): RecyclerView.ViewHolder(item) {
    var nftImageView: ImageView=item.findViewById(R.id.nftImageView)
}