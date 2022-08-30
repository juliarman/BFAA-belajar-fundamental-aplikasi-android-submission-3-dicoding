package com.juliarman.appsgithubuser.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.juliarman.appsgithubuser.R
import com.juliarman.appsgithubuser.ThemeViewModelFactory
import com.juliarman.appsgithubuser.activity.viewmodel.ThemeViewModel
import com.juliarman.appsgithubuser.databinding.ActivityThemeBinding
import com.juliarman.appsgithubuser.helper.SettingPreferences

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val preferences = SettingPreferences.getInstance(dataStore)

        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(preferences))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(
            this

        ) { isDarkModeActive: Boolean ->

            if (isDarkModeActive) {


                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.apply {
                    switchMode.isChecked = true
                    darkMode.setImageResource(R.drawable.ic_dark_mode_on)
                    lightMode.setImageResource(R.drawable.ic_light_mode_off)
                    statusTheme.text = resources.getString(R.string.dark_mode_activated)
                }

            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.apply {
                    switchMode.isChecked = false
                    darkMode.setImageResource(R.drawable.ic_dark_mode_off)
                    lightMode.setImageResource(R.drawable.ic_light_mode_on)
                    statusTheme.text = resources.getString(R.string.light_mode_activated)
                }
            }

        }


        binding.switchMode.setOnCheckedChangeListener{_: CompoundButton?, isChecked: Boolean ->


            themeViewModel.saveThemeSetting(isChecked)

        }




        settingActionBar()





    }

    private fun settingActionBar() {


        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.elevation = 0F

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}