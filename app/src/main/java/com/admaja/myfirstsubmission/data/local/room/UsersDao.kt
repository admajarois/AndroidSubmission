package com.admaja.myfirstsubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.admaja.myfirstsubmission.data.local.entity.UsersEntity

interface UsersDao {

    @Query("SELECT * FROM users")
    fun getUser(): LiveData<List<UsersEntity>>

    @Query("SELECT * FROM users where favorited = 1")
    fun getFavoritedUser(): LiveData<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: List<UsersEntity>)

    @Update
    fun updateNews(user: UsersEntity)

    @Query("DELETE FROM users where favorited = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM users where id=:id AND favorited=1)")
    fun isUserFavorited(id: Int): Boolean
}