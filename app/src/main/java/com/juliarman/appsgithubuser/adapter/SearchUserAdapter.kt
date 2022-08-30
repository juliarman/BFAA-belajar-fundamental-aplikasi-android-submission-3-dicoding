package com.juliarman.appsgithubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juliarman.appsgithubuser.R
import com.juliarman.appsgithubuser.databinding.ListUsersBinding
import com.juliarman.appsgithubuser.model.ItemsItem

class SearchUserAdapter(private val listSearchUser: List<ItemsItem>, private val context: Context) : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {


    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {

        fun onItemClicked(dataUsers: ItemsItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){

        this.onItemClickCallback = onItemClickCallback

    }
    class ViewHolder(var binding: ListUsersBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemUsers: ItemsItem){

            binding.login.text = itemUsers.login
            Glide.with(context)
                .load(itemUsers.avatarUrl)
                .thumbnail(0.05f)
                .placeholder(R.color.orange)
                .into(binding.imgUsers)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ListUsersBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding, context)

    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listSearch = listSearchUser[position]

        holder.bind(listSearch)
        holder.binding.root.setOnClickListener {
            onItemClickCallback.onItemClicked(listSearchUser[position])
        }



    }

    override fun getItemCount() = listSearchUser.size
}