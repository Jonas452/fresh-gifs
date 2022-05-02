package com.jonas.freshgifs.data.remote.response

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("fixed_height") val fixedHeight: ImageSizeResponse,
)

data class ImageSizeResponse(
    @SerializedName("url") val url: String,
)
