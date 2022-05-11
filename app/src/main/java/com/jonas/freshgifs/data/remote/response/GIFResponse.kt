package com.jonas.freshgifs.data.remote.response

import com.google.gson.annotations.SerializedName

data class GIFResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("images") val image: ImageResponse,
)
