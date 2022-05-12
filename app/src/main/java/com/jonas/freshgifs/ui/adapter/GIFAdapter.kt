package com.jonas.freshgifs.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jonas.freshgifs.domain.model.GIF

class GIFAdapter(
    private val layoutType: GIFListLayoutType,
    private val addFavoriteGIF: (gif: GIF) -> Unit,
    private val removeFavoriteGIF: (gif: GIF) -> Unit,
): ListAdapter<GIF, RecyclerView.ViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is GifSingleColumnViewHolder -> holder.bind(getItem(position), addFavoriteGIF, removeFavoriteGIF)
            is GifDoubleColumnViewHolder -> holder.bind(getItem(position), addFavoriteGIF, removeFavoriteGIF)
            else -> throw IllegalStateException("view holder type not supported of GifAdapter")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(layoutType) {
            GIFListLayoutType.SINGLE_COLUMN -> GifSingleColumnViewHolder.from(parent)
            GIFListLayoutType.DOUBLE_COLUMN -> GifDoubleColumnViewHolder.from(parent)
        }
    }
}
enum class GIFListLayoutType {
    SINGLE_COLUMN,
    DOUBLE_COLUMN
}

class PostDiffCallback : DiffUtil.ItemCallback<GIF>() {
    override fun areItemsTheSame(oldItem: GIF, newItem: GIF): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GIF,
        newItem: GIF
    ): Boolean {
        return oldItem == newItem
    }
}
