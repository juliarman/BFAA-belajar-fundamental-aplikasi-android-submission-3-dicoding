package com.juliarman.appsgithubuser.api

import com.juliarman.appsgithubuser.BuildConfig
import com.juliarman.appsgithubuser.model.DetailUsersResponse
import com.juliarman.appsgithubuser.model.ItemsItem
import com.juliarman.appsgithubuser.model.SearchUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_TOKEN}")
    fun searchUsers(
        @Query("q") query: String
    ) : Call<SearchUsersResponse>


    @GET("users/{login}")
    @Headers("Authorization: token ${BuildConfig.API_TOKEN}")
    fun detailUsers(
        @Path("login") login: String
    ) : Call<DetailUsersResponse>


    @GET("users/{login}/followers")
    @Headers("Authorization: token ${BuildConfig.API_TOKEN}")
    fun getFollower(
        @Path("login") login: String
    ) : Call<List<ItemsItem>>


    @GET("users/{login}/following")
    @Headers("Authorization: token ${BuildConfig.API_TOKEN}")
    fun getFollowing(
        @Path("login") login: String
    ) : Call<List<ItemsItem>>

}