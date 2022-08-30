package com.juliarman.appsgithubuser.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.juliarman.appsgithubuser.R
import com.juliarman.appsgithubuser.ThemeViewModelFactory
import com.juliarman.appsgithubuser.activity.viewmodel.MainViewModel
import com.juliarman.appsgithubuser.adapter.SearchUserAdapter
import com.juliarman.appsgithubuser.databinding.ActivityMainBinding
import com.juliarman.appsgithubuser.helper.CheckNetworkConnection
import com.juliarman.appsgithubuser.helper.SettingPreferences
import com.juliarman.appsgithubuser.model.ItemsItem


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private var listUserGithub = ArrayList<ItemsItem>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name ="theme_setting")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkConnection()

        val preferences = SettingPreferences.getInstance(dataStore)

        viewModel = ViewModelProvider(this, ThemeViewModelFactory(preferences))[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)


        viewModel.getThemeSettings().observe(this){isDarkModeActive ->

            if (isDarkModeActive){

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        viewModel.searchGithubUsers.observe(this){ search->
            setUserGithub(search)
        }

        viewModel.totalCountUsers.observe(this){ count->

            message(count)

        }

        viewModel.isLoading.observe(this) { loading->
            showLoading(loading)
        }



        searchUsersGithub()



    }



    private fun showLoading(isLoading: Boolean) {

        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun message(totalCountUsers: Int?) {

        if (totalCountUsers == 0) binding.viewNotFound.visibility = View.VISIBLE
        else binding.viewNotFound.visibility = View.GONE

        val hide =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hide.hideSoftInputFromWindow(binding.rvUsers.windowToken, 0)

    }

    private fun searchUsersGithub() {


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                viewModel.searchUser(query)
                setUserGithub(listUserGithub)
                return true

            }

            override fun onQueryTextChange(newQuery: String?): Boolean {



                return false

            }

        })





    }

    private fun setUserGithub(listUserGithub: List<ItemsItem>) {

        val list = ArrayList<ItemsItem>()
        for (listUser in list){
            list.clear()
            list.addAll(listUserGithub)
        }



        val adapter = SearchUserAdapter(listUserGithub, this)
        binding.rvUsers.adapter = adapter


        adapter.setOnItemClickCallback(object : SearchUserAdapter.OnItemClickCallback {
            override fun onItemClicked(dataUsers: ItemsItem) {

                detailUsers(dataUsers)

            }

        })


    }

    private fun detailUsers(dataUsers: ItemsItem) {

        val intent = Intent(this@MainActivity, DetailUsersActivity::class.java)
        intent.putExtra(DetailUsersActivity.EXTRA_LOGIN, dataUsers.login)
        startActivity(intent)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        viewModel.getThemeSettings().observe(this){ isDarkModeActive ->

            if (isDarkModeActive){
                menuInflater.inflate(R.menu.top_up_bar_dark,menu)
            } else{
                menuInflater.inflate(R.menu.top_up_bar_light,menu)
            }

        }


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.themeMode -> {

                val intentTheme = Intent(this@MainActivity, ThemeActivity::class.java)
                startActivity(intentTheme)

            }

            R.id.favorite -> {

                val intentFavorit = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intentFavorit)
            }

            R.id.exit -> {

                finishAndRemoveTask()

            }

        }

        return super.onOptionsItemSelected(item)
    }


    private fun networkConnection() {

        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->

            if (isConnected) {

                setSupportActionBar(binding.toolbar)
                binding.apply {
                    toolbar.visibility = View.VISIBLE
                    searchView.visibility = View.VISIBLE
                    titleToolbar.visibility = View.VISIBLE
                    noConnection.visibility = View.GONE
                }

            } else {

                binding.apply {
                    toolbar.visibility = View.GONE
                    searchView.visibility = View.GONE
                    titleToolbar.visibility = View.GONE
                    noConnection.visibility = View.VISIBLE
                }

            }

        }

    }





}