package com.jonas.freshgifs.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.freshgifs.domain.model.GIF
import com.jonas.freshgifs.domain.usecase.AddFavoriteGIFUseCase
import com.jonas.freshgifs.domain.usecase.GetAllFavoriteGIFSUseCase
import com.jonas.freshgifs.domain.usecase.RemoveFavoriteGIFUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    getAllFavoriteGIFSUseCase: GetAllFavoriteGIFSUseCase,
    private val addFavoriteGIFUseCase: AddFavoriteGIFUseCase,
    private val removeFavoriteGIFUseCase: RemoveFavoriteGIFUseCase,
): ViewModel() {

    val allFavoriteGIFS: StateFlow<FavoriteUIState> =
        getAllFavoriteGIFSUseCase()
        .map {
            FavoriteUIState.Success(it)
        }.catch {
            FavoriteUIState.Error
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            FavoriteUIState.Loading,
        )

    fun addFavoriteGIF(gif: GIF) {
        viewModelScope.launch {
            addFavoriteGIFUseCase(gif)
        }
    }

    fun removeFavoriteGIF(gif: GIF) {
        viewModelScope.launch {
            removeFavoriteGIFUseCase(gif)
        }
    }
}
