package com.jonas.freshgifs.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.jonas.freshgifs.data.local.entity.FavoriteGIFEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavoriteGIFEntity(
    @ColumnInfo(name = COLUMN_ID) var id: String,
    @ColumnInfo(name = COLUMN_URL) var url: String,
) {

    companion object {
        const val TABLE_NAME = "favorite_gif"

        const val COLUMN_ID = "id"
        const val COLUMN_URL = "url"

    }
}
