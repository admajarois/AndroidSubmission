package com.admaja.myfirstsubmission.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.admaja.myfirstsubmission.data.api.ApiService
import com.admaja.myfirstsubmission.data.api.UsersResponse
import com.admaja.myfirstsubmission.data.local.entity.UsersEntity
import com.admaja.myfirstsubmission.data.local.room.UsersDao
import com.admaja.myfirstsubmission.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository private constructor(
    private val apiService: ApiService,
    private val usersDao: UsersDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<UsersEntity>>>()

    fun getUsers(query: String): LiveData<Result<List<UsersEntity>>> {
        result.value = Result.Loading
        val client = apiService.getUser(query)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    val userList = ArrayList<UsersEntity>()
                    appExecutors.diskIO.execute {
                        users?.forEach{ user ->
                            val isFavorited = usersDao.isUserFavorited(user.id)

                            val person = UsersEntity(
                                user.id,
                                user.login,
                                user.avatarUrl,
                                isFavorited
                            )
                            userList.add(person)
                        }
                        usersDao.deleteAll()
                        usersDao.insertUser(userList)
                    }
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = usersDao.getUser()
        result.addSource(localData) {newData: List<UsersEntity> ->
            result.value = Result.Succes(newData)
        }
        return result
    }

    fun getFavoritedUser(): LiveData<List<UsersEntity>> {
        return usersDao.getFavoritedUser()
    }

    fun setFavoritedUser(users: UsersEntity, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            users.isFavorited = favoriteState
            usersDao.updateNews(users)
        }
    }

    companion object {
        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            apiService: ApiService,
            usersDao: UsersDao,
            appExecutors: AppExecutors
        ): UsersRepository = instance?: synchronized(this) {
            instance?: UsersRepository(apiService, usersDao, appExecutors)
        }.also { instance = it }
    }
}