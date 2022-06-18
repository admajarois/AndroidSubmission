package com.admaja.myfirstsubmission.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") login: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String?
    ): Call<ArrayList<FollowersResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<FollowingResponse>
}