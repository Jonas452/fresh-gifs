package com.jonas.freshgifs.ui.favorite

import com.jonas.freshgifs.domain.model.GIF

sealed class FavoriteUIState {
    object Loading : FavoriteUIState()
    class Success(val gifs: List<GIF>) : FavoriteUIState()
    object Error : FavoriteUIState()
}
