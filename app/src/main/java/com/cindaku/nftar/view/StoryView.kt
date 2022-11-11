package com.cindaku.nftar.view

import com.cindaku.nftar.db.entity.NFT

interface StoryView {
    fun onClick(nft: NFT?)
    fun onDownloadRequest(nft: NFT?)
}