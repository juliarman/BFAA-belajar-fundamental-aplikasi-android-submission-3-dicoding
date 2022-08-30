package com.juliarman.appsgithubuser.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.juliarman.appsgithubuser.activity.DetailUsersActivity
import com.juliarman.appsgithubuser.activity.viewmodel.FollowerViewModel
import com.juliarman.appsgithubuser.adapter.FollowUserAdapter
import com.juliarman.appsgithubuser.databinding.FragmentFollowerBinding
import com.juliarman.appsgithubuser.model.ItemsItem

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var followerViewModel: FollowerViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentFollowerBinding.inflate(inflater, container,false)
        return binding.root

    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        followerViewModel.login.observe(viewLifecycleOwner) { listFollower ->

            if (listFollower.isEmpty()){

                binding.apply {
                    noFollower.visibility = View.VISIBLE
                    rvFragmentFollower.visibility = View.GONE
                }

            } else{
                setFollowerFragment(listFollower)
            }

        }

        followerViewModel.getFollower(
            arguments?.getString(DetailUsersActivity.EXTRA_FRAGMENT).toString()
        )

        followerViewModel.isLoading.observe(viewLifecycleOwner){ loading ->

            showLoading(loading)

        }

    }

    private fun showLoading(isLoading: Boolean) {

        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setFollowerFragment(listFollower: List<ItemsItem>) {

        val list = ArrayList<ItemsItem>()

        for (follower in listFollower){

            list.clear()
            list.addAll(listFollower)
        }

        binding.rvFragmentFollower.layoutManager = LinearLayoutManager(context)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFragmentFollower.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFragmentFollower.addItemDecoration(itemDecoration)


        val adapter = FollowUserAdapter(listFollower)
        binding.rvFragmentFollower.adapter = adapter

        adapter.setOnItemClickCallback(object : FollowUserAdapter.OnItemClickCallback {
            override fun onItemClicked(listFollower: ItemsItem) {

                detailFollowers(listFollower)

            }

        })


    }

    private fun detailFollowers(listFollower: ItemsItem) {
        val intent = Intent(activity, DetailUsersActivity::class.java)
        intent.putExtra(DetailUsersActivity.EXTRA_LOGIN, listFollower.login)
        startActivity(intent)
    }


}