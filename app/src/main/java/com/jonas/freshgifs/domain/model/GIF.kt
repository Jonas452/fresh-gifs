package com.jonas.freshgifs.domain.model

data class GIF (
    val id: String,
    val title: String,
    val url: String,
    val isFavorite: Boolean,
)
