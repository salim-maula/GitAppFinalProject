package com.salim.finalprojectgitappsalim.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.salim.finalprojectgitappsalim.response.ModelUser
import android.view.LayoutInflater
import com.salim.finalprojectgitappsalim.R
import com.salim.finalprojectgitappsalim.databinding.ItemRowFavoriteBinding

class AdapterUser: RecyclerView.Adapter<AdapterUser.UsersViewHolder>() {
    private val listView = ArrayList<ModelUser>()
    private var onItemClickCallBack: OnItemClickCallBack? = null

    fun setOnItemClickCallBack (onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setUserList(users:ArrayList<ModelUser>){
        listView.clear()
        listView.addAll(users)
        notifyDataSetChanged()
    }
    inner class UsersViewHolder(val binding: ItemRowFavoriteBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(users : ModelUser){
            binding.root.setOnClickListener {
                onItemClickCallBack?.onItemClick(users)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(users.avatar_url)
                    .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_account_circle))
                    .into(avatarFavorite)
                tvLogin.text = users.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = ItemRowFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(listView[position])
    }

    override fun getItemCount(): Int = listView.size



    interface OnItemClickCallBack{
        fun onItemClick(data: ModelUser)
    }
}


