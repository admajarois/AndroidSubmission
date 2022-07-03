package com.admaja.myfirstsubmission.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.admaja.myfirstsubmission.data.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val _user = MutableLiveData<ArrayList<ItemsItem>>()
    val user: LiveData<ArrayList<ItemsItem>> = _user

    private val _followers = MutableLiveData<ArrayList<FollowResponseItem>>()
    val followers: LiveData<ArrayList<FollowResponseItem>> = _followers

    private val _following = MutableLiveData<ArrayList<FollowResponseItem>>()
    val following: LiveData<ArrayList<FollowResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _noData = MutableLiveData<Boolean>()
    val noData:LiveData<Boolean> = _noData

    init {
        _noData.value = true
        _isLoading.value = false
    }

    fun setFollowers(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object : Callback<ArrayList<FollowResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<FollowResponseItem>>,
                response: Response<ArrayList<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun setFollowing(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<ArrayList<FollowResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<FollowResponseItem>>,
                response: Response<ArrayList<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun setSearchUser(query: String) {
        _isLoading.value = true
        _noData.value = false
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                _isLoading.value = false
                _noData.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                }else {
                    Log.e(TAG, "On Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.value = false
                _noData.value = true
                Log.e(TAG, "On Failure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
