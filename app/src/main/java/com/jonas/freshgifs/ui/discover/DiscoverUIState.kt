package com.jonas.freshgifs.ui.discover

import com.jonas.freshgifs.domain.model.GIF

sealed class DiscoverUIState {
    object Empty : DiscoverUIState()
    object Loading : DiscoverUIState()
    class Success(val gifs: List<GIF>) : DiscoverUIState()
    object Error : DiscoverUIState()
}
