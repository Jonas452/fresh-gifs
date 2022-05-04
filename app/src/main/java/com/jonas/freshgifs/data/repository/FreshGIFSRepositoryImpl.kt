package com.jonas.freshgifs.data.repository

import com.jonas.freshgifs.data.local.dao.FavoriteGIFDAO
import com.jonas.freshgifs.data.local.mapper.GIFLocalMapper
import com.jonas.freshgifs.data.remote.FreshGIFSRemoteDataSource
import com.jonas.freshgifs.data.remote.mapper.GIFRemoteMapper
import com.jonas.freshgifs.di.IOContext
import com.jonas.freshgifs.domain.model.GIF
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FreshGIFSRepositoryImpl @Inject constructor(
    private val freshGIFSRemoteDataSource: FreshGIFSRemoteDataSource,
    private val gifRemoteMapper: GIFRemoteMapper,
    private val favoriteGIFDao: FavoriteGIFDAO,
    private val gifLocalMapper: GIFLocalMapper,
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

    override suspend fun insert(gif: GIF) =
        withContext(coroutineContext) {
            favoriteGIFDao.insert(
                gifLocalMapper.toEntity(gif)
            )
        }

    override suspend fun getFavoriteGIFById(id: String): GIF? =
        withContext(coroutineContext) {
            val favoriteGIF = favoriteGIFDao.getFavoriteGIFById(id) ?: return@withContext null
            return@withContext gifLocalMapper.toDomainModel(favoriteGIF)
        }

    override suspend fun delete(gif: GIF) =
        withContext(coroutineContext) {
            favoriteGIFDao.delete(
                gifLocalMapper.toEntity(gif)
            )
        }

    override fun getAllFavoriteGIFS(): Flow<List<GIF>> =
        favoriteGIFDao.getAllFavoriteGIFS().map {
            it.map { favoriteGIFEntity -> gifLocalMapper.toDomainModel(favoriteGIFEntity) }
        }
}
