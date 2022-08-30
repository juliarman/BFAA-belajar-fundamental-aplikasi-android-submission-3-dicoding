package com.juliarman.appsgithubuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.juliarman.appsgithubuser.activity.viewmodel.MainViewModel
import com.juliarman.appsgithubuser.activity.viewmodel.ThemeViewModel
import com.juliarman.appsgithubuser.helper.SettingPreferences

class ThemeViewModelFactory(private val preferences: SettingPreferences): ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)){

            return ThemeViewModel(preferences) as T

        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)){

            return MainViewModel(preferences) as T

        }

        throw IllegalArgumentException("Unknown ViewModel class: "+modelClass.name)

    }
}