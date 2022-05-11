package com.jonas.freshgifs.util

import com.jonas.freshgifs.data.remote.response.BaseGIFResponse
import com.jonas.freshgifs.data.remote.response.GIFResponse
import com.jonas.freshgifs.data.remote.response.ImageResponse
import com.jonas.freshgifs.data.remote.response.ImageSizeResponse
import com.jonas.freshgifs.domain.model.GIF

fun createBaseGIFResponse(
    data: List<GIFResponse> = listOf(createGIFResponse())
) = BaseGIFResponse(
    data = data,
)

fun createGIFResponse(
    id: String = "qwe1r221134",
    title: String = "gif title",
    image: ImageResponse = createImageResponse(),
) = GIFResponse(
    id = id,
    title = title,
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

fun createGIF(
    id: String = "1rqwrq1",
    title: String = "gif title",
    url: String = "empty_url",
    isFavorite: Boolean = false,
) = GIF(
    id = id,
    title = title,
    url = url,
    isFavorite = isFavorite,
)
