package com.jonas.freshgifs.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jonas.freshgifs.data.local.entity.FavoriteGIFEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavoriteGIFEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: String,
    @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_URL) val url: String,
) {

    companion object {
        const val TABLE_NAME = "favorite_gif"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_URL = "url"

    }
}
