package com.juliarman.appsgithubuser.activity.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juliarman.appsgithubuser.api.ApiConfig
import com.juliarman.appsgithubuser.db.Favorite
import com.juliarman.appsgithubuser.model.DetailUsersResponse
import com.juliarman.appsgithubuser.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {


    private val _login = MutableLiveData<DetailUsersResponse>()
    val login: LiveData<DetailUsersResponse> = _login


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteUserRepository: FavoriteRepository =
        FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteUserRepository.getAllFavorite()

    fun insertData(user: Favorite){

        mFavoriteUserRepository.insert(user)

    }

    fun delete(user: Favorite){

        mFavoriteUserRepository.delete(user)

    }

    fun getDetailUsers(login: String){


        _isLoading.value = true

        val client = ApiConfig.getApiService().detailUsers(login)
        client.enqueue(object : Callback<DetailUsersResponse> {
            override fun onResponse(
                call: Call<DetailUsersResponse>,
                response: Response<DetailUsersResponse>
            ) {


                _isLoading.value = false

                if (response.isSuccessful){

                    _login.value = response.body()

                }

            }

            override fun onFailure(call: Call<DetailUsersResponse>, t: Throwable) {


                _isLoading.value = false

            }


        })


    }

}