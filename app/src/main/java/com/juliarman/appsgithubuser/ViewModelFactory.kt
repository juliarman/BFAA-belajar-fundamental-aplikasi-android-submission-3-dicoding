package com.juliarman.appsgithubuser

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juliarman.appsgithubuser.activity.viewmodel.DetailViewModel
import com.juliarman.appsgithubuser.activity.viewmodel.FavoriteViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory(){




    companion object{

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory{

            if (INSTANCE == null){

                synchronized(ViewModelFactory::class.java){

                    INSTANCE = ViewModelFactory(application)

                }

            }

            return INSTANCE as ViewModelFactory

        }

    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Uknown ViewModel class: ${modelClass.name}")
    }




}