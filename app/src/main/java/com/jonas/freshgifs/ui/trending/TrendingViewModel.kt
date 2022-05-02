package com.jonas.freshgifs.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonas.freshgifs.domain.usecase.GetTrendingGIFSUseCase
import com.jonas.freshgifs.domain.usecase.SearchGIFSUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val getTrendingGIFSUseCase: GetTrendingGIFSUseCase,
    private val searchGIFSUseCase: SearchGIFSUseCase,
): ViewModel() {

    private val _trendingUIState =
        MutableStateFlow<TrendingUIState>(TrendingUIState.Empty)
    val trendingUIState: StateFlow<TrendingUIState> = _trendingUIState

    fun getTrendingGIFS() {
        viewModelScope.launch {
            try {
                _trendingUIState.value = TrendingUIState.Loading
                val gifs = getTrendingGIFSUseCase()
                gifs.forEach {  gif ->
                    println(gif.id)
                }
                _trendingUIState.value = TrendingUIState.Success(gifs)
            }catch (e: Exception) {
                e.printStackTrace()
                _trendingUIState.value = TrendingUIState.Error
            }
        }
    }
}