package com.jonas.freshgifs.data.repository

import com.google.common.truth.Truth.assertThat
import com.jonas.freshgifs.data.local.dao.FavoriteGIFDAO
import com.jonas.freshgifs.data.local.mapper.GIFLocalMapper
import com.jonas.freshgifs.data.remote.FreshGIFSAPI
import com.jonas.freshgifs.data.remote.mapper.GIFRemoteMapper
import com.jonas.freshgifs.domain.model.GIF
import com.jonas.freshgifs.util.createBaseGIFResponse
import com.jonas.freshgifs.util.createGIFResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FreshGIFSRepositoryTest {

    private lateinit var subject: FreshGIFSRepository

    private val freshGIFSAPI: FreshGIFSAPI = mockk()
    private val favoriteGIFDao: FavoriteGIFDAO = mockk()
    private val gifRemoteMapper = GIFRemoteMapper()
    private val gifLocalMapper = GIFLocalMapper()

    @Before
    fun setup() {
        subject = FreshGIFSRepositoryImpl(
            freshGIFSAPI,
            gifRemoteMapper,
            favoriteGIFDao,
            gifLocalMapper,
            Dispatchers.IO,
        )
    }

    @Test
    fun `when getting trending gifs returns list of gifs`() = runTest {
        // Arrange
        val gifResponse = listOf(
            createGIFResponse(),
            createGIFResponse(),
            createGIFResponse(),
        )
        coEvery {
            freshGIFSAPI.getTrendingGIFS(any())
        } returns createBaseGIFResponse(gifResponse)

        // Act
        val result = subject.getTrendingGIFS("")

        // Assert
        assertThat(result.size).isEqualTo(gifResponse.size)
        assertThat(result[0]).isInstanceOf(GIF::class.java)
        assertThat(result[0].id).isEqualTo(gifResponse[0].id)
    }
}
