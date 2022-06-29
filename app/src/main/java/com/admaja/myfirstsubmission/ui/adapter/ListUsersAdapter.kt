package com.admaja.myfirstsubmission.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.admaja.myfirstsubmission.data.api.ItemsItem
import com.admaja.myfirstsubmission.databinding.ItemRowProfileBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class ListUsersAdapter(private val list: ArrayList<ItemsItem>): RecyclerView.Adapter<ListUsersAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    fun setList(user: ArrayList<ItemsItem>) {
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bin(list[position])
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(list[holder.adapterPosition])
        }
    }

    override fun getItemCount()= list.size

    inner class UserViewHolder(val binding: ItemRowProfileBinding): RecyclerView.ViewHolder(binding.root) {
        fun bin(user: ItemsItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgItemPhoto)
                tvItemUsername.text = user.login
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}