package com.admaja.myfirstsubmission.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.admaja.myfirstsubmission.api.FollowingResponseItem
import com.admaja.myfirstsubmission.databinding.ItemRowProfileBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FollowingAdapter(private val list_following: ArrayList<FollowingResponseItem>): RecyclerView.Adapter<FollowingAdapter.FollowingHolder>() {
    class FollowingHolder(val binding: ItemRowProfileBinding): RecyclerView.ViewHolder(binding.root) {
        fun bin(following: FollowingResponseItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(following.avatarUrl)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgItemPhoto)
                tvItemUsername.text = following.login
            }
        }

    }

    fun setList(following: ArrayList<FollowingResponseItem>) {
        list_following.clear()
        list_following.addAll(following)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingHolder {
        val view = ItemRowProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingHolder, position: Int) {
        holder.bin(list_following[position])
    }

    override fun getItemCount(): Int = list_following.size
}