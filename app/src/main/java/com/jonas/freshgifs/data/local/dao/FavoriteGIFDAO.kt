package com.jonas.freshgifs.data.local.dao

import androidx.room.*
import com.jonas.freshgifs.data.local.entity.FavoriteGIFEntity
import com.jonas.freshgifs.data.local.entity.FavoriteGIFEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGIFDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteGIFEntity: FavoriteGIFEntity)

    @Query(
        """
            SELECT * FROM $TABLE_NAME WHERE id = :id
        """
    )
    suspend fun getFavoriteGIFById(id: String): FavoriteGIFEntity?

    @Delete
    suspend fun delete(favoriteGIFEntity: FavoriteGIFEntity)

    @Query(
        """
            SELECT * FROM $TABLE_NAME
        """
    )
    fun getAllFavoriteGIFS(): Flow<List<FavoriteGIFEntity>>
}