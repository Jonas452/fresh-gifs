package com.jonas.freshgifs.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jonas.freshgifs.data.local.entity.FavoriteGIFEntity
import com.jonas.freshgifs.data.local.dao.FavoriteGIFDAO

@Database(
    entities = [
        FavoriteGIFEntity::class,
    ],
    version = 1,
)
abstract class FreshGIFDatabase: RoomDatabase() {

    abstract fun getFavoriteGIFDao(): FavoriteGIFDAO

    companion object {
        private const val DB_NAME = "fresh_gifs.db"

        @JvmStatic
        fun newInstance(context: Context) = Room.databaseBuilder(
            context,
            FreshGIFDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
