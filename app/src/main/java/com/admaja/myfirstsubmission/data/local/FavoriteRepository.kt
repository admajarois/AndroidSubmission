package com.admaja.myfirstsubmission.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.admaja.myfirstsubmission.data.api.ApiService
import com.admaja.myfirstsubmission.data.local.entity.FavoriteEntity
import com.admaja.myfirstsubmission.data.local.room.FavoriteDao
import com.admaja.myfirstsubmission.utils.AppExecutors
import com.admaja.myfirstsubmission.data.Result
import com.admaja.myfirstsubmission.data.api.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
){
    fun getFavoriteUser(): LiveData<List<FavoriteEntity>> = favoriteDao.getFavorite()

    fun setFavoritedUser(favoriteUser: FavoriteEntity) {
        appExecutors.diskIO.execute {
            favoriteDao.insertFavorite(favoriteUser)
        }
    }

    fun deleteFavorite(id: Int) {
        favoriteDao.deleteFavorite(id)
    }

    fun isFavorited(id: Int): Boolean? {
        var favorited:Boolean? = null
        appExecutors.diskIO.execute {
            favorited = favoriteDao.isUsersFavorited(id)
        }
        return favorited
    }


    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository = instance?: synchronized(this) {
            instance?: FavoriteRepository(favoriteDao, appExecutors)
        }.also { instance = it }
    }
}