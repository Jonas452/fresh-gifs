package com.jonas.freshgifs.util

import com.jonas.freshgifs.data.remote.response.BaseGIFResponse
import com.jonas.freshgifs.data.remote.response.GIFResponse
import com.jonas.freshgifs.data.remote.response.ImageResponse
import com.jonas.freshgifs.data.remote.response.ImageSizeResponse

fun createBaseGIFResponse(
    data: List<GIFResponse> = listOf(createGIFResponse())
) = BaseGIFResponse(
    data = data,
)

fun createGIFResponse(
    id: String = "qwe1r221134",
    image: ImageResponse = createImageResponse(),
) = GIFResponse(
    id = id,
    image = image,
)

fun createImageResponse(
    fixedHeight: ImageSizeResponse = createImageSizeResponse()
) = ImageResponse(
    fixedHeight = fixedHeight,
)

fun createImageSizeResponse(
    url: String = "empty_url"
) = ImageSizeResponse(
    url = url,
)
