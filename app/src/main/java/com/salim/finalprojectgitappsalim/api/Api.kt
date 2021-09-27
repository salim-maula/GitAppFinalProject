package com.salim.finalprojectgitappsalim.api

import com.salim.finalprojectgitappsalim.response.ModelUser
import com.salim.finalprojectgitappsalim.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("users/{username}")
    @Headers("Authorization: token 8227166fe9012ef510908a6df73b85b2a49c205e")
    fun getDetailUser(
        @Path("username") username: String?
    ): Call<ModelUser>

    @GET("search/users")
    @Headers("Authorization: token 8227166fe9012ef510908a6df73b85b2a49c205e")
    fun getSearchUser(
        @Query("q") q: String?
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 8227166fe9012ef510908a6df73b85b2a49c205e")
    fun getFollowersUser(
        @Path("username") username: String?
    ): Call<ArrayList<ModelUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 8227166fe9012ef510908a6df73b85b2a49c205e")
    fun getFollowingUser(
        @Path("username") username: String?
    ): Call<ArrayList<ModelUser>>
}