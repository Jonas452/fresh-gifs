package com.jonas.freshgifs.ui.trending

import com.jonas.freshgifs.domain.model.GIF

sealed class TrendingUIState {
    object Empty : TrendingUIState()
    object Loading : TrendingUIState()
    class Success(val gifs: List<GIF>) : TrendingUIState()
    object Error : TrendingUIState()
}
