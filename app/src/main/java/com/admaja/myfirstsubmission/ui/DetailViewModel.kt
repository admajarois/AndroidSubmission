package com.admaja.myfirstsubmission.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.admaja.myfirstsubmission.data.api.ApiConfig
import com.admaja.myfirstsubmission.data.api.DetailResponse
import com.admaja.myfirstsubmission.data.local.FavoriteRepository
import com.admaja.myfirstsubmission.data.local.entity.FavoriteEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class DetailViewModel(private val favoriteRepository: FavoriteRepository):ViewModel() {

    private val _detail = MutableLiveData<DetailResponse>()
    val detail: LiveData<DetailResponse> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _isLoading.value = false
    }

    fun detailUsers(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    fun getFavoriteUser() = favoriteRepository.getFavoriteUser()

    fun saveUser(favorite: FavoriteEntity) {
        favoriteRepository.setFavoritedUser(favorite)
    }

    fun deleteUser(id: Int) {
        favoriteRepository.deleteFavorite(id)
    }

    fun isFavorited(id: Int) = favoriteRepository.isFavorited(id)

    companion object {
        private const val TAG = "DetailViewModel"
    }
}