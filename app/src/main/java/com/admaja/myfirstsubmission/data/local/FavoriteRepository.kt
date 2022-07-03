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
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<FavoriteEntity>>>()

    fun getDetailUser(login: String): LiveData<Result<List<FavoriteEntity>>> {
        result.value = Result.Loading
        val client = apiService.getDetailUser(login)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    val detail = response.body()
                    val favoriteList = ArrayList<FavoriteEntity>()
                    appExecutors.diskIO.execute {
                        val isFavorited = detail?.let { favoriteDao.isUsersFavorited(it.id) }
                        val favorite = FavoriteEntity(
                            detail?.id,
                            detail?.login,
                            detail?.name.toString(),
                            detail?.avatarUrl,
                            detail?.publicRepos,
                            detail?.followers,
                            detail?.following,
                            isFavorited
                        )
                        favoriteList.add(favorite)
                        favoriteDao.deleteAll()
                        favoriteDao.insertFavorite(favoriteList)
                    }
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        val localData = favoriteDao.getFavorite()
        result.addSource(localData) {newData: List<FavoriteEntity> ->
            result.value = Result.Succes(newData)
        }
        return result
    }

    fun getFavoritedUsers():  LiveData<List<FavoriteEntity>> {
        return favoriteDao.getFavoritedUser()
    }

    fun setFavoritedUser(favoriteUser: FavoriteEntity, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            favoriteUser.isFavorited = favoriteState
            favoriteDao.updateFavorite(favoriteUser)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository = instance?: synchronized(this) {
            instance?: FavoriteRepository(apiService, favoriteDao, appExecutors)
        }.also { instance = it }
    }
}