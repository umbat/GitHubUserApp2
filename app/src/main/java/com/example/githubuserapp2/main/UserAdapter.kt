package com.example.githubuserapp2.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserapp2.data.model.User
import com.example.githubuserapp2.databinding.ItemListUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        infix fun bind(user: User){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView.context)
                    .load(user. avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUser)
                tvUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemListUserBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
       viewHolder bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}