package com.example.githubuserapp2.data.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse (

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,

)