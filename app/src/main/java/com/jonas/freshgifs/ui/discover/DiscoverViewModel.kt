package com.jonas.freshgifs.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.freshgifs.domain.model.GIF
import com.jonas.freshgifs.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getTrendingGIFSUseCase: GetTrendingGIFSUseCase,
    private val searchGIFSUseCase: SearchGIFSUseCase,
    private val addFavoriteGIFUseCase: AddFavoriteGIFUseCase,
    private val removeFavoriteGIFUseCase: RemoveFavoriteGIFUseCase,
    private val revalidateFavoriteGIFSUseCase: RevalidateFavoriteGIFSUseCase,
): ViewModel() {

    private val _discoverUIState =
        MutableStateFlow<DiscoverUIState>(DiscoverUIState.Empty)
    val discoverUIState: StateFlow<DiscoverUIState> = _discoverUIState

    private val loadingScope = CoroutineScope(Dispatchers.Main + Job())

    fun getTrendingGIFS() {
        loadingScope.launch {
            try {
                _discoverUIState.value = DiscoverUIState.Loading
                val gifs = getTrendingGIFSUseCase()
                _discoverUIState.value = DiscoverUIState.Success(gifs)
            }catch (e: Exception) {
                e.printStackTrace()
                _discoverUIState.value = DiscoverUIState.Error
            }
        }
    }

    fun searchGIFS(query: String) {
        loadingScope.coroutineContext.cancelChildren()
        loadingScope.launch {
            try {
                _discoverUIState.value = DiscoverUIState.Loading
                val gifs = searchGIFSUseCase(query)
                _discoverUIState.value = DiscoverUIState.Success(gifs)
            }catch (e: Exception) {
                e.printStackTrace()
                _discoverUIState.value = DiscoverUIState.Error
            }
        }
    }

    fun addFavoriteGIF(gif: GIF) {
        viewModelScope.launch {
            addFavoriteGIFUseCase(gif)
            revalidateFavoriteGIFS()
        }
    }

    fun removeFavoriteGIF(gif: GIF) {
        viewModelScope.launch {
            removeFavoriteGIFUseCase(gif)
            revalidateFavoriteGIFS()
        }
    }

    /*
        Look at the RevalidateFavoriteGIFSUseCase for explanation
     */
    private suspend fun revalidateFavoriteGIFS() {
        val currentUIState = _discoverUIState.value
        if(currentUIState is DiscoverUIState.Success) {
            val revalidatedItems = revalidateFavoriteGIFSUseCase(currentUIState.gifs)
            _discoverUIState.value = DiscoverUIState.Success(revalidatedItems)
        }
    }
}
