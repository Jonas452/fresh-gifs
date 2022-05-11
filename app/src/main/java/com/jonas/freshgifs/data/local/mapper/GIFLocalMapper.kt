package com.jonas.freshgifs.data.local.mapper

import com.jonas.freshgifs.data.local.entity.FavoriteGIFEntity
import com.jonas.freshgifs.domain.model.GIF
import javax.inject.Inject

class GIFLocalMapper @Inject constructor() {

    fun toEntity(domainModel: GIF) =
        FavoriteGIFEntity(
            id = domainModel.id,
            title = domainModel.title,
            url = domainModel.url,
        )

    fun toDomainModel(entity: FavoriteGIFEntity) =
        GIF(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            isFavorite = true,
        )
}
