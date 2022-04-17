package com.example.githubuserapp2.api

import com.example.githubuserapp2.data.model.DetailUserResponse
import com.example.githubuserapp2.data.model.User
import com.example.githubuserapp2.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}