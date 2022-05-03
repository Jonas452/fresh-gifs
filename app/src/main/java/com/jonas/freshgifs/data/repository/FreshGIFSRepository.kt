package com.jonas.freshgifs.data.repository

import com.jonas.freshgifs.domain.model.GIF
import kotlinx.coroutines.flow.Flow

interface FreshGIFSRepository {

    suspend fun getTrendingGIFS(apiKey: String): List<GIF>

    suspend fun searchGIFS(
        apiKey: String,
        query: String,
    ): List<GIF>

    suspend fun insert(gif: GIF)

    suspend fun getFavoriteGIFById(id: String): GIF?

    suspend fun delete(gif: GIF)

    fun getAllFavoriteGIFS(): Flow<List<GIF>>
}
