package com.jonas.freshgifs.data.repository

import com.jonas.freshgifs.data.remote.FreshGIFSRemoteDataSource
import com.jonas.freshgifs.data.remote.mapper.GIFRemoteMapper
import com.jonas.freshgifs.di.IOContext
import com.jonas.freshgifs.domain.model.GIF
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FreshGIFSRepositoryImpl @Inject constructor(
    private val freshGIFSRemoteDataSource: FreshGIFSRemoteDataSource,
    private val gifRemoteMapper: GIFRemoteMapper,
    @IOContext private val coroutineContext: CoroutineContext,
): FreshGIFSRepository {

    override suspend fun getTrendingGIFS(apiKey: String): List<GIF> =
        withContext(coroutineContext) {
            return@withContext freshGIFSRemoteDataSource.getTrendingGIFS(apiKey).data.map {
                gifRemoteMapper.fromGIFResponse(it)
            }
        }

    override suspend fun searchGIFS(apiKey: String, query: String): List<GIF> =
        withContext(coroutineContext) {
            return@withContext freshGIFSRemoteDataSource.searchGIFS(apiKey, query).data.map {
                gifRemoteMapper.fromGIFResponse(it)
            }
        }
}
