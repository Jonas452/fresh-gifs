package com.jonas.freshgifs.data.repository

import com.jonas.freshgifs.data.remote.FreshGIFSRemoteDataSource
import com.jonas.freshgifs.data.remote.mapper.GIFRemoteMapper
import com.jonas.freshgifs.domain.model.GIF
import javax.inject.Inject

class FreshGIFSRepositoryImpl @Inject constructor(
    private val freshGIFSRemoteDataSource: FreshGIFSRemoteDataSource,
    private val gifRemoteMapper: GIFRemoteMapper,
): FreshGIFSRepository {

    override suspend fun getTrendingGIFS(apiKey: String): List<GIF> {
        return freshGIFSRemoteDataSource.getTrendingGIFS(apiKey).data.map {
            gifRemoteMapper.fromGIFResponse(it)
        }
    }

    override suspend fun searchGIFS(apiKey: String, query: String): List<GIF> {
        return freshGIFSRemoteDataSource.searchGIFS(apiKey, query).data.map {
            gifRemoteMapper.fromGIFResponse(it)
        }
    }
}
