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
import com.juliarman.appsgithubuser.databinding.FragmentFollowingBinding
import com.juliarman.appsgithubuser.model.ItemsItem

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel.login.observe(viewLifecycleOwner) {listFollowing ->

            if (listFollowing.isEmpty()){

                binding.apply {

                    noFollowing.visibility = View.VISIBLE
                    rvFragmentFollowing.visibility = View.GONE

                }

            }else{
                setFollowingFragment(listFollowing)
            }

        }

        followerViewModel.getFollowing(

            arguments?.getString(DetailUsersActivity.EXTRA_FRAGMENT).toString()
        )

        followerViewModel.isLoading.observe(viewLifecycleOwner){

            showLoading(it)
        }

    }

    private fun showLoading(isLoading: Boolean) {

        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setFollowingFragment(listFollowing: List<ItemsItem>) {

        val list = ArrayList<ItemsItem>()

        for (following in listFollowing){

            list.clear()
            list.addAll(listFollowing)

        }

        binding.rvFragmentFollowing.layoutManager = LinearLayoutManager(context)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFragmentFollowing.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFragmentFollowing.addItemDecoration(itemDecoration)

        val adapter = FollowUserAdapter(listFollowing)
        binding.rvFragmentFollowing.adapter = adapter


        adapter.setOnItemClickCallback(object : FollowUserAdapter.OnItemClickCallback {
            override fun onItemClicked(listFollower: ItemsItem) {

                detailFollowing(listFollower)
            }

        })

    }


    private fun detailFollowing(listFollower: ItemsItem) {
        val intent = Intent(activity, DetailUsersActivity::class.java)
        intent.putExtra(DetailUsersActivity.EXTRA_LOGIN, listFollower.login)
        startActivity(intent)
    }

}