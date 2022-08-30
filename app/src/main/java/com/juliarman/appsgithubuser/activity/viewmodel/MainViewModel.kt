package com.juliarman.appsgithubuser.activity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.juliarman.appsgithubuser.api.ApiConfig
import com.juliarman.appsgithubuser.helper.SettingPreferences
import com.juliarman.appsgithubuser.model.ItemsItem
import com.juliarman.appsgithubuser.model.SearchUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {

    companion object{

        private const val TAG = "MainViewModel"
    }


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalCOuntUsers = MutableLiveData<Int>()
    val totalCountUsers: LiveData<Int> = _totalCOuntUsers

    private val _searchGithubUsers = MutableLiveData<List<ItemsItem>>()
    val searchGithubUsers: LiveData<List<ItemsItem>> = _searchGithubUsers



    fun getThemeSettings(): LiveData<Boolean>{

        return preferences.getThemeSetting().asLiveData()

    }

    fun searchUser(query: String){


        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : Callback<SearchUsersResponse> {
            override fun onResponse(
                call: Call<SearchUsersResponse>,
                response: Response<SearchUsersResponse>
            ) {


                _isLoading.value = false

                if (response.isSuccessful){

                    _totalCOuntUsers.value = response.body()?.totalCount
                    _searchGithubUsers.value = response.body()?.items

                } else {

                    Log.e(TAG, "onFailure: ${response.message()}")
                }



            }

            override fun onFailure(call: Call<SearchUsersResponse>, t: Throwable) {

                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")


            }

        })

    }

}