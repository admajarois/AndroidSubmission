package com.admaja.myfirstsubmission.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") login: String
    ): Call<UserResponse>
}