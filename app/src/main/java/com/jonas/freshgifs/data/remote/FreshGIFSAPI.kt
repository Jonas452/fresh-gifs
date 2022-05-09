package com.jonas.freshgifs.data.remote

import com.jonas.freshgifs.data.remote.response.BaseGIFResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FreshGIFSAPI {

    @GET(TRENDING_ENDPOINT)
    suspend fun getTrendingGIFS(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int = 20,
    ): BaseGIFResponse

    @GET(FAVORITE_ENDPOINT)
    suspend fun searchGIFS(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 20,
    ): BaseGIFResponse

    private companion object {
        const val TRENDING_ENDPOINT = "gifs/trending"
        const val FAVORITE_ENDPOINT = "gifs/search"
    }
}
