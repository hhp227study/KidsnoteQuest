package com.hhp227.kidsnotequest.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("id") val id: String,
    @SerialName("author") val author: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("url") val url: String,
    @SerialName("download_url") val downloadUrl: String
) : java.io.Serializable