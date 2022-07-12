package com.admaja.myfirstsubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.admaja.myfirstsubmission.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_user")
    fun getFavorite(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favoriteUser: FavoriteEntity)

    @Query("DELETE FROM favorite_user WHERE id= :id")
    fun deleteFavorite(id: Int?)

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE id = :id)")
    fun isUsersFavorited(id: Int?): LiveData<Boolean>
}