package com.juliarman.appsgithubuser.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juliarman.appsgithubuser.api.ApiConfig
import com.juliarman.appsgithubuser.model.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    private val _login = MutableLiveData<List<ItemsItem>>()
    val login: LiveData<List<ItemsItem>> = _login

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    
    fun getFollower(login: String){

        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(login)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {

                _isLoading.value = false
                if (response.isSuccessful){

                    _login.value = response.body()
                }

            }


            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {

                _isLoading.value = false
            }

        })
        
    }

    fun getFollowing(login: String){

        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {

                _isLoading.value = false
                if (response.isSuccessful){
                    _login.value = response.body()
                }

            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {

                _isLoading.value = false


            }

        })

    }

}