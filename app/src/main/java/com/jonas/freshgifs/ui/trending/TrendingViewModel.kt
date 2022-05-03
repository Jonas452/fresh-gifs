package com.jonas.freshgifs.ui.trending

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
class TrendingViewModel @Inject constructor(
    private val getTrendingGIFSUseCase: GetTrendingGIFSUseCase,
    private val searchGIFSUseCase: SearchGIFSUseCase,
    private val addFavoriteGIFUseCase: AddFavoriteGIFUseCase,
    private val removeFavoriteGIFUseCase: RemoveFavoriteGIFUseCase,
    private val revalidateFavoriteGIFSUseCase: RevalidateFavoriteGIFSUseCase,
): ViewModel() {

    private val _trendingUIState =
        MutableStateFlow<TrendingUIState>(TrendingUIState.Empty)
    val trendingUIState: StateFlow<TrendingUIState> = _trendingUIState

    private val loadingScope = CoroutineScope(Dispatchers.Main + Job())

    fun getTrendingGIFS() {
        loadingScope.launch {
            try {
                _trendingUIState.value = TrendingUIState.Loading
                val gifs = getTrendingGIFSUseCase()
                _trendingUIState.value = TrendingUIState.Success(gifs)
            }catch (e: Exception) {
                e.printStackTrace()
                _trendingUIState.value = TrendingUIState.Error
            }
        }
    }

    fun searchGIFS(query: String) {
        loadingScope.coroutineContext.cancelChildren()
        loadingScope.launch {
            try {
                _trendingUIState.value = TrendingUIState.Loading
                val gifs = searchGIFSUseCase(query)
                _trendingUIState.value = TrendingUIState.Success(gifs)
            }catch (e: Exception) {
                e.printStackTrace()
                _trendingUIState.value = TrendingUIState.Error
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

    private suspend fun revalidateFavoriteGIFS() {
        val currentUIState = _trendingUIState.value
        if(currentUIState is TrendingUIState.Success) {
            val revalidatedItems = revalidateFavoriteGIFSUseCase(currentUIState.gifs)
            _trendingUIState.value = TrendingUIState.Success(revalidatedItems)
        }
    }
}
