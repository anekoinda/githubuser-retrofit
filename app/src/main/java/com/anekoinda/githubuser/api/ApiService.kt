package com.anekoinda.githubuser.api

import com.anekoinda.githubuser.model.User
import com.anekoinda.githubuser.model.UserDetailResponse
import com.anekoinda.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token masukin token github disini")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token masukin token github disini")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token masukin token github disini")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token masukin token github disini")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}