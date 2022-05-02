package com.jonas.freshgifs.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseGIFResponse(
    @SerializedName("data") val data: List<GIFResponse>,
)
