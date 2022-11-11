package com.cindaku.nftar.model

data class NFTMetadata(
    var title: String?,
    var description: String?,
    var media: String?,
    var media_hash: String?,
    var copies: Int?,
    var issued_at: String?,
    var expires_at: String?,
    var starts_at: String?,
    var updated_at: String?,
    var extra: String?,
    var reference: String?,
    var reference_hash: String?,
)
