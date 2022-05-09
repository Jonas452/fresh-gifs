package com.jonas.freshgifs.data.repository

import com.jonas.freshgifs.data.local.dao.FavoriteGIFDAO
import com.jonas.freshgifs.data.local.mapper.GIFLocalMapper
import com.jonas.freshgifs.data.remote.FreshGIFSAPI
import com.jonas.freshgifs.data.remote.mapper.GIFRemoteMapper
import com.jonas.freshgifs.di.IODispatcher
import com.jonas.freshgifs.domain.model.GIF
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FreshGIFSRepositoryImpl @Inject constructor(
    private val freshGIFSAPI: FreshGIFSAPI,
    private val gifRemoteMapper: GIFRemoteMapper,
    private val favoriteGIFDao: FavoriteGIFDAO,
    private val gifLocalMapper: GIFLocalMapper,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
): FreshGIFSRepository {

    override suspend fun getTrendingGIFS(apiKey: String): List<GIF> =
        withContext(coroutineDispatcher) {
            return@withContext freshGIFSAPI.getTrendingGIFS(apiKey).data.map {
                gifRemoteMapper.fromGIFResponse(it)
            }
        }

    override suspend fun searchGIFS(apiKey: String, query: String): List<GIF> =
        withContext(coroutineDispatcher) {
            return@withContext freshGIFSAPI.searchGIFS(apiKey, query).data.map {
                gifRemoteMapper.fromGIFResponse(it)
            }
        }

    override suspend fun insert(gif: GIF) =
        withContext(coroutineDispatcher) {
            favoriteGIFDao.insert(
                gifLocalMapper.toEntity(gif)
            )
        }

    override suspend fun getFavoriteGIFById(id: String): GIF? =
        withContext(coroutineDispatcher) {
            val favoriteGIF = favoriteGIFDao.getFavoriteGIFById(id) ?: return@withContext null
            return@withContext gifLocalMapper.toDomainModel(favoriteGIF)
        }

    override suspend fun delete(gif: GIF) =
        withContext(coroutineDispatcher) {
            favoriteGIFDao.delete(
                gifLocalMapper.toEntity(gif)
            )
        }

    override fun getAllFavoriteGIFS(): Flow<List<GIF>> =
        favoriteGIFDao.getAllFavoriteGIFS().map {
            it.map { favoriteGIFEntity -> gifLocalMapper.toDomainModel(favoriteGIFEntity) }
        }
}
