package com.jonas.freshgifs.ui.trending

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
import com.jonas.freshgifs.databinding.FragmentTrendingBinding
import com.jonas.freshgifs.domain.model.GIF
import com.jonas.freshgifs.ui.trending.adapter.GIFAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingFragment : Fragment() {

    private val viewModel by viewModels<TrendingViewModel>()
    private lateinit var binding: FragmentTrendingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner

        loadGIFS()
        setupObservers()
        setupClickListeners()

        return binding.root
    }

    private fun loadGIFS() {
        viewModel.getTrendingGIFS()
        // TODO check if is to get trending or search
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.trendingUIState.collect { state ->
                    when(state) {
                        TrendingUIState.Empty -> {}
                        TrendingUIState.Loading -> handleLoading()
                        is TrendingUIState.Success -> handleSuccess(state.gifs)
                        TrendingUIState.Error -> handleError()
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.tryAgainButton.setOnClickListener {
            loadGIFS()
        }
    }

    private fun handleLoading() {
        showLayout(LayoutType.LOADING)
    }

    private fun handleSuccess(gifs: List<GIF>) {
        showLayout(LayoutType.GIFS)

        val gifAdapter = GIFAdapter(requireContext())
        gifAdapter.submitList(gifs)
        binding.gifsList.adapter = gifAdapter
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
        fun newInstance() =
            TrendingFragment()
    }
}
