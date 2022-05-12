package com.jonas.freshgifs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonas.freshgifs.R
import com.jonas.freshgifs.databinding.GifDoubleColumnListItemBinding
import com.jonas.freshgifs.domain.model.GIF

class GifDoubleColumnViewHolder constructor(
    private val context: Context,
    private val binding: GifDoubleColumnListItemBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: GIF,
        addFavoriteGIF: (gif: GIF) -> Unit,
        removeFavoriteGIF: (gif: GIF) -> Unit,
    ) {
        binding.gifTitle.text = item.title
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
        fun from(parent: ViewGroup): GifDoubleColumnViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = GifDoubleColumnListItemBinding.inflate(layoutInflater, parent, false)

            return GifDoubleColumnViewHolder(parent.context, binding)
        }
    }
}