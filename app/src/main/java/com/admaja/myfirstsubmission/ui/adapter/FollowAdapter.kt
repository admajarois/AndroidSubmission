package com.admaja.myfirstsubmission.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.admaja.myfirstsubmission.api.FollowResponseItem
import com.admaja.myfirstsubmission.databinding.ItemRowProfileBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FollowAdapter(private val list_followers: ArrayList<FollowResponseItem>): RecyclerView.Adapter<FollowAdapter.FollowersVieHolder>() {

    class FollowersVieHolder(val binding: ItemRowProfileBinding): RecyclerView.ViewHolder(binding.root) {
        fun bin(followers: FollowResponseItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(followers.avatarUrl)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgItemPhoto)
                tvItemUsername.text = followers.login
            }
        }

    }

    fun setList(followers: ArrayList<FollowResponseItem>) {
        list_followers.clear()
        list_followers.addAll(followers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersVieHolder {
        val view = ItemRowProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersVieHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersVieHolder, position: Int) {
        holder.bin(list_followers[position])
    }

    override fun getItemCount(): Int = list_followers.size
}