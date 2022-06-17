package com.admaja.myfirstsubmission.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.admaja.myfirstsubmission.api.ApiConfig
import com.admaja.myfirstsubmission.api.DetailResponse
import com.admaja.myfirstsubmission.api.ItemsItem
import com.admaja.myfirstsubmission.api.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val _user = MutableLiveData<ArrayList<ItemsItem>>()
    val user: LiveData<ArrayList<ItemsItem>> = _user

    private val _detail = MutableLiveData<DetailResponse>()
    val detail: LiveData<DetailResponse> = _detail

    fun detailUsers(user: String) {
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    _detail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    fun setSearchUser(query: String) {
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                }else {
                    Log.e(TAG, "On Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e(TAG, "On Failure: ${t.message.toString()}")
            }

        })
    }
//
    fun getSearchUser(): LiveData<ArrayList<ItemsItem>> {
        return user
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
