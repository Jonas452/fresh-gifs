package com.jonas.freshgifs.data.repository

import com.jonas.freshgifs.domain.model.GIF

interface FreshGIFSRepository {

    suspend fun getTrendingGIFS(apiKey: String): List<GIF>

    suspend fun searchGIFS(
        apiKey: String,
        query: String,
    ): List<GIF>
}
