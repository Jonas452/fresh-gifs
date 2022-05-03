package com.jonas.freshgifs.ui.trending.adapter

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

class GIFAdapter(private val context: Context) :
    ListAdapter<GIF, GIFAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: GifItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: GIF) {
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
