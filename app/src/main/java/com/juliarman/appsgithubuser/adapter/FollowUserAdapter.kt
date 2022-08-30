package com.juliarman.appsgithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juliarman.appsgithubuser.R
import com.juliarman.appsgithubuser.adapter.FollowUserAdapter.ViewHolder
import com.juliarman.appsgithubuser.databinding.ListUsersBinding
import com.juliarman.appsgithubuser.model.ItemsItem

class FollowUserAdapter(private val listFollowUserAdapter: List<ItemsItem>) : RecyclerView.Adapter<ViewHolder>() {


    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(followItem: ItemsItem){

            binding.login.text = followItem.login
            Glide.with(binding.root.context)
                .load(followItem.avatarUrl)
                .thumbnail(0.05f)
                .placeholder(R.color.orange)
                .into(binding.imgUsers)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val binding = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listFollowUser = listFollowUserAdapter[position]
        holder.bind(listFollowUser)
        holder.binding.root.setOnClickListener {

            onItemClickCallback.onItemClicked(listFollowUserAdapter[position])

        }

    }

    interface OnItemClickCallback {

        fun onItemClicked(listFollower: ItemsItem)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){

        this.onItemClickCallback = onItemClickCallback

    }

    override fun getItemCount() = listFollowUserAdapter.size
}