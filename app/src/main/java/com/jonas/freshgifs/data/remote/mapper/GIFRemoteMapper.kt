package com.jonas.freshgifs.data.remote.mapper

import com.jonas.freshgifs.data.remote.response.GIFResponse
import com.jonas.freshgifs.domain.model.GIF
import javax.inject.Inject

class GIFRemoteMapper @Inject constructor() {

    fun fromGIFResponse(gifResponse: GIFResponse) =
        GIF(
            id = gifResponse.id,
            url = gifResponse.image.fixedHeight.url,
        )
}
