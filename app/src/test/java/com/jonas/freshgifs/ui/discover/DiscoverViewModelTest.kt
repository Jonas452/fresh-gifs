package com.jonas.freshgifs.ui.discover

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.jonas.freshgifs.domain.usecase.*
import com.jonas.freshgifs.util.createGIF
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DiscoverViewModelTest {

    private lateinit var subject: DiscoverViewModel

    private val getTrendingGIFSUseCase: GetTrendingGIFSUseCase = mockk()
    private val searchGIFSUseCase: SearchGIFSUseCase = mockk()
    private val addFavoriteGIFUseCase: AddFavoriteGIFUseCase = mockk()
    private val removeFavoriteGIFUseCase: RemoveFavoriteGIFUseCase = mockk()
    private val revalidateFavoriteGIFSUseCase: RevalidateFavoriteGIFSUseCase = mockk()
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setup() {
        testDispatcher = UnconfinedTestDispatcher()

        subject = DiscoverViewModel(
            getTrendingGIFSUseCase,
            searchGIFSUseCase,
            addFavoriteGIFUseCase,
            removeFavoriteGIFUseCase,
            revalidateFavoriteGIFSUseCase,
            testDispatcher,
        )
    }

    @Test
    fun `when getting trending gifs should return loading as state`() = runTest {
        // Arrange
        val gifsList = listOf(
            createGIF(),
            createGIF(),
            createGIF(),
        )
        coEvery { getTrendingGIFSUseCase() } coAnswers  {
            delay(100)
            gifsList
        }

        // Act
        val results = mutableListOf<DiscoverUIState>()
        val job = launch(testDispatcher) {
            subject.discoverUIState.toList(results)
        }
        subject.getTrendingGIFS()

        // Assert
        assertThat(results[1]).isInstanceOf(DiscoverUIState.Loading::class.java)
        job.cancel()
    }
}
