package com.admaja.myfirstsubmission.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.admaja.myfirstsubmission.api.ApiConfig
import com.admaja.myfirstsubmission.api.UserResponse
import com.admaja.myfirstsubmission.models.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setSearchUser(query: String) {
        val client = ApiConfig.getApiService().getUser(query)
            client.enqueue(object: Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("Failure","on Failure: ${t.message}")
                }
            })
    }

    fun getSearchUser(): LiveData<ArrayList<Users>>{
        return listUsers
    }
}