package com.juliarman.appsgithubuser.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.juliarman.appsgithubuser.R
import com.juliarman.appsgithubuser.ViewModelFactory
import com.juliarman.appsgithubuser.activity.viewmodel.DetailViewModel
import com.juliarman.appsgithubuser.adapter.SectionsPageAdapter
import com.juliarman.appsgithubuser.databinding.ActivityDetailUsersBinding
import com.juliarman.appsgithubuser.db.Favorite
import com.juliarman.appsgithubuser.model.DetailUsersResponse

class DetailUsersActivity : AppCompatActivity() {



    private lateinit var binding: ActivityDetailUsersBinding
    private lateinit var detailViewModel: DetailViewModel


    private var favorite: Favorite? = null
    private var buttonFavorite: Boolean = false

    private var detailUser = DetailUsersResponse()

    companion object{

        const val EXTRA_FRAGMENT = "extra_fragment"
        const val EXTRA_LOGIN = "extra_login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)


        setContentView(binding.root)


        settingActionBar()

        detailViewModel = obtainViewModel(this@DetailUsersActivity)




        val userIntent = intent.extras
        val userLogin = userIntent?.getString(EXTRA_LOGIN)


        if (userLogin != null) {
            detailViewModel.getDetailUsers(userLogin)

        }

        val login= Bundle()
        login.putString(EXTRA_FRAGMENT, userLogin)


        detailViewModel.isLoading.observe(this){loading ->

            showLoading(loading)

        }



        detailViewModel.login.observe(this) { detailList ->

            detailUser = detailList

            setDetailData(detailList)

            favorite = Favorite(detailUser.id, detailUser.login, detailUser.avatarUrl)

            detailViewModel.getAllFavorite().observe(this) { listFavorite ->

                if (listFavorite !== null) {

                    for (data in listFavorite) {

                        if (detailUser.id == data.id) {

                            buttonFavorite = true
                            binding.btnFavorite.setImageResource(R.drawable.ic_favorited)
                        }

                    }

                }

            }


            binding.btnFavorite.setOnClickListener {




                if (!buttonFavorite){

                    buttonFavorite = true
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorited)
                    insertToDatabase(detailUser)
                } else{

                    buttonFavorite = false
                    binding.btnFavorite.setImageResource(R.drawable.ic_unfavorit)
                    detailViewModel.delete(favorite as Favorite)
                    Toast.makeText(this@DetailUsersActivity, "${detailUser.login} has been successfully removed from favorites!", Toast.LENGTH_SHORT).show()


                }

            }
            

        }


        val sectionsPageAdapter = SectionsPageAdapter(this, login)
        val viewPager: ViewPager2 =  findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPageAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager){ tab, position ->

            tab.text  = resources.getString(TAB_TITLES[position])

        }.attach()



    }

    private fun settingActionBar() {

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.elevation = 0F
    }

    private fun obtainViewModel(detailUsersActivity: AppCompatActivity): DetailViewModel {

        val factory = ViewModelFactory.getInstance(detailUsersActivity.application)
        return ViewModelProvider(detailUsersActivity, factory)[DetailViewModel::class.java]

    }



    private fun insertToDatabase(detailList: DetailUsersResponse) {


        favorite.let { favorite ->

            favorite?.id = detailList.id
            favorite?.login = detailList.login
            detailViewModel.insertData(favorite as Favorite)

            Toast.makeText(this@DetailUsersActivity, "${detailList.login} has been successfully added to favorites!", Toast.LENGTH_SHORT).show()

        }


    }

    private fun setDetailData(detailUserGithub: DetailUsersResponse) {


        binding.apply {
            tvName.text = detailUserGithub.name ?: resources.getString(R.string.name_not_set)
            tvLocation.text = detailUserGithub.location ?: resources.getString(R.string.location_not_set)
            tvEmail.text = detailUserGithub.email ?: resources.getString(R.string.email_not_set)
            tvCompany.text = detailUserGithub.company ?: resources.getString(R.string.company_not_set)

            tvBlog.text = detailUserGithub.blog ?: resources.getString(R.string.blog_not_set)
            if (detailUserGithub.blog!!.isEmpty()){
                tvBlog.text = resources.getString(R.string.blog_not_set)
            }

            tvFollowers.text = detailUserGithub.followers.toString()
            tvFollowing.text = detailUserGithub.following.toString()
            tvRepositori.text =detailUserGithub.publicRepos.toString()
        }

        Glide.with(this)
            .load(detailUserGithub.avatarUrl)
            .thumbnail(0.05f)
            .placeholder(R.color.orange)
            .into(binding.imgUsers)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean){

        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }





}