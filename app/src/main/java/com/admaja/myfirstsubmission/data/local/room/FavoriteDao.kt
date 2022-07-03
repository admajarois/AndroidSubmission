package com.admaja.myfirstsubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.admaja.myfirstsubmission.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_user")
    fun getFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_user WHERE favorited = 1")
    fun getFavoritedUser(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favoriteUser: List<FavoriteEntity>)

    @Update
    fun updateFavorite(favoriteUser: FavoriteEntity)

    @Query("DELETE FROM favorite_user WHERE favorited = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE id = :id AND favorited=1)")
    fun isUsersFavorited(id: Int): Boolean
}