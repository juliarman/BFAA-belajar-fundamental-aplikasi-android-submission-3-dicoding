package com.juliarman.appsgithubuser.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.juliarman.appsgithubuser.ViewModelFactory
import com.juliarman.appsgithubuser.activity.viewmodel.FavoriteViewModel
import com.juliarman.appsgithubuser.adapter.FavoriteAdapter
import com.juliarman.appsgithubuser.databinding.ActivityFavoriteBinding
import com.juliarman.appsgithubuser.db.Favorite

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        settingActionBar()

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllFavorite().observe(this) { favoriteList ->

            if (favoriteList.isEmpty()) {


              binding.apply {
                  layoutMessage.visibility = View.VISIBLE
                  rvFavorite.visibility = View.GONE
              }


            } else{

                adapter.setFavorite(favoriteList)
               binding.apply {
                   layoutMessage.visibility = View.GONE
                   rvFavorite.visibility = View.VISIBLE
               }
            }

        }

        adapter = FavoriteAdapter()

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        binding.rvFavorite.addItemDecoration(itemDecoration)
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)

        binding.rvFavorite.setHasFixedSize(false)
        binding.rvFavorite.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(listFavorite: Favorite) {

                detailFavorite(listFavorite)

            }

        })

    }

    private fun detailFavorite(listFavorite: Favorite) {

        val intent = Intent(this@FavoriteActivity, DetailUsersActivity::class.java)
        intent.putExtra(DetailUsersActivity.EXTRA_LOGIN, listFavorite.login)
        startActivity(intent)

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {

        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory)[FavoriteViewModel::class.java]

    }

    private fun settingActionBar() {

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.elevation = 0F
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return  true
    }
}