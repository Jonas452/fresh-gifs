package com.jonas.freshgifs.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jonas.freshgifs.databinding.FragmentDiscoverBinding
import com.jonas.freshgifs.domain.model.GIF
import com.jonas.freshgifs.ui.adapter.GIFAdapter
import com.jonas.freshgifs.ui.adapter.GIFListLayoutType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private val viewModel by viewModels<DiscoverViewModel>()
    private lateinit var binding: FragmentDiscoverBinding

    private lateinit var gifAdapter: GIFAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupUIListeners()
        setupAdapters()
        setupObservers()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadGIFS()
    }

    private fun loadGIFS() {
        val queryText = binding.searchGIFInputText.text.toString()
        if (queryText.isNotEmpty()) {
            viewModel.searchGIFS(queryText)
        } else {
            viewModel.getTrendingGIFS()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.discoverUIState.collect { state ->
                    Log.d("UI_STATE", state.toString())
                    when (state) {
                        DiscoverUIState.Empty -> {}
                        DiscoverUIState.Loading -> handleLoading()
                        is DiscoverUIState.Success -> handleSuccess(state.gifs)
                        DiscoverUIState.Error -> handleError()
                    }
                }
            }
        }
    }

    private fun setupUIListeners() {
        binding.tryAgainButton.setOnClickListener {
            loadGIFS()
        }

        binding.searchGIFInputText.doOnTextChanged { text, _, _, _ ->
            if (text != null && text.length >= MINIMUM_TEXT_SIZE_SEARCH) {
                loadGIFS()
            }
        }
    }

    private fun setupAdapters() {
        gifAdapter = GIFAdapter(
            GIFListLayoutType.SINGLE_COLUMN,
            ::addFavoriteGIF,
            ::removeFavoriteGIF,
        )
        binding.gifsList.adapter = gifAdapter
    }

    private fun handleLoading() {
        showLayout(LayoutType.LOADING)
    }

    private fun handleSuccess(gifs: List<GIF>) {
        showLayout(LayoutType.GIFS)
        gifAdapter.submitList(gifs)
    }

    private fun addFavoriteGIF(gif: GIF) {
        viewModel.addFavoriteGIF(gif)
    }

    private fun removeFavoriteGIF(gif: GIF) {
        viewModel.removeFavoriteGIF(gif)
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
            LayoutType.GIFS to binding.gifsList,
            LayoutType.ERROR to binding.errorLayout,
        )
    }

    private enum class LayoutType { LOADING, GIFS, ERROR }

    companion object {
        private const val MINIMUM_TEXT_SIZE_SEARCH = 2

        fun newInstance() =
            DiscoverFragment()
    }
}
