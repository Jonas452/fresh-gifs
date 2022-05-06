package com.jonas.freshgifs.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jonas.freshgifs.databinding.FragmentFavoriteBinding
import com.jonas.freshgifs.domain.model.GIF
import com.jonas.freshgifs.ui.adapter.GIFAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var binding: FragmentFavoriteBinding

    private lateinit var gifAdapter: GIFAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupAdapters()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allFavoriteGIFS.collect { state ->
                    when(state) {
                        FavoriteUIState.Loading -> handleLoading()
                        is FavoriteUIState.Success -> handleSuccess(state.gifs)
                        FavoriteUIState.Error -> handleError()
                    }
                }
            }
        }
    }

    private fun setupAdapters() {
        gifAdapter = GIFAdapter(
            ::addFavoriteGIF,
            ::removeFavoriteGIF,
        )
        binding.gifsList.adapter = gifAdapter
    }

    private fun addFavoriteGIF(gif: GIF) {
        viewModel.addFavoriteGIF(gif)
    }

    private fun removeFavoriteGIF(gif: GIF) {
        viewModel.removeFavoriteGIF(gif)
    }

    private fun handleLoading() {
        showLayout(LayoutType.LOADING)
    }

    private fun handleSuccess(gifs: List<GIF>) {
        showLayout(LayoutType.FAVORITE_GIFS)
        gifAdapter.submitList(gifs)
    }

    private fun handleError() {
        showLayout(LayoutType.ERROR)
    }

    private fun showLayout(layoutType: LayoutType) {
        LayoutType.values().forEach {
            val view = layoutMapper[it]
            view?.isVisible = layoutType == it
        }
    }

    private val layoutMapper by lazy {
        mapOf<LayoutType, View>(
            LayoutType.LOADING to binding.loadingLayout,
            LayoutType.FAVORITE_GIFS to binding.gifsList,
            LayoutType.ERROR to binding.errorLayout,
        )
    }

    private enum class LayoutType { LOADING, FAVORITE_GIFS, ERROR }

    companion object {
        fun newInstance() =
            FavoriteFragment()
    }
}
