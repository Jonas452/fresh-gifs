package com.jonas.freshgifs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonas.freshgifs.R
import com.jonas.freshgifs.databinding.GifItemBinding
import com.jonas.freshgifs.domain.model.GIF

class GIFAdapter(
    private val context: Context,
    private val addFavoriteGIF: (gif: GIF) -> Unit,
    private val removeFavoriteGIF: (gif: GIF) -> Unit,
) :
    ListAdapter<GIF, GIFAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, getItem(position), addFavoriteGIF, removeFavoriteGIF)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: GifItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            context: Context,
            item: GIF,
            addFavoriteGIF: (gif: GIF) -> Unit,
            removeFavoriteGIF: (gif: GIF) -> Unit,
        ) {
            binding.favoriteButton.text = if(item.isFavorite) {
                context.getString(R.string.unfavorite_gif)
            }else {
                context.getString(R.string.favorite_gif)
            }

            binding.favoriteButton.setOnClickListener {
                if(item.isFavorite) {
                    removeFavoriteGIF.invoke(item)
                }else {
                    addFavoriteGIF.invoke(item)
                }
            }

            Glide.with(context)
                .load(item.url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.error_sad)
                .into(binding.gifImageView)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GifItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
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
