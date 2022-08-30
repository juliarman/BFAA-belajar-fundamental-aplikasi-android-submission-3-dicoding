package com.juliarman.appsgithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juliarman.appsgithubuser.R
import com.juliarman.appsgithubuser.databinding.ListUsersBinding
import com.juliarman.appsgithubuser.db.Favorite
import com.juliarman.appsgithubuser.helper.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {

        fun onItemClicked(listFavorite: Favorite)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private val listFavorite = ArrayList<Favorite>()

    fun setFavorite(favorite: List<Favorite>){


        val diffCallback = FavoriteDiffCallback(this.listFavorite, favorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(favorite)
        diffResult.dispatchUpdatesTo(this)

    }

    class FavoriteViewHolder(var binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {

            binding.login.text = favorite.login
            Glide.with(binding.root.context)
                .load(favorite.avatarUrl)
                .thumbnail(0.05f)
                .placeholder(R.color.teal_700)
                .into(binding.imgUsers)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {

        val itemFavorite = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteViewHolder(itemFavorite)

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {


        val favorite = listFavorite[position]
        holder.bind(favorite)
        holder.binding.root.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavorite[position])
        }

    }

    override fun getItemCount() = listFavorite.size
}