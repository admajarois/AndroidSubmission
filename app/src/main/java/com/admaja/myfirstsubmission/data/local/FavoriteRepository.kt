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

    private val result = MediatorLiveData<Result<List<FavoriteEntity>>>()
    private val isFavorite = MediatorLiveData<Result<Boolean>>()

    fun getFavoriteUser(): LiveData<Result<List<FavoriteEntity>>> {
        result.value = Result.Loading
        val localData = favoriteDao.getFavorite()
        result.addSource(localData) {
            newData: List<FavoriteEntity> ->
            result.value = Result.Succes(newData)
        }
        return result
    }

    fun setFavoritedUser(favoriteUser: FavoriteEntity) {
        appExecutors.diskIO.execute {
            favoriteDao.insertFavorite(favoriteUser)
        }
    }

    fun deleteFavorite(id: Int) {
        appExecutors.diskIO.execute {
            favoriteDao.deleteFavorite(id)
        }
    }

    fun isFavorited(id: Int): LiveData<Result<Boolean>> {
        isFavorite.value = Result.Loading
        val favorited = favoriteDao.isUsersFavorited(id)
        isFavorite.addSource(favorited) {
            isFavorite.value = Result.Succes(it)
        }
        return isFavorite
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