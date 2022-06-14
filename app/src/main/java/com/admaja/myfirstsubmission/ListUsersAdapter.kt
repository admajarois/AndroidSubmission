package com.admaja.myfirstsubmission

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.admaja.myfirstsubmission.databinding.ItemRowProfileBinding
import com.admaja.myfirstsubmission.models.Users
import com.bumptech.glide.Glide

class ListUsersAdapter(private val listUsers: ArrayList<Users>): RecyclerView.Adapter<ListUsersAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemCallback
    }

    class ListViewHolder(var binding: ItemRowProfileBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, username, location,repository, company, followers, following, photo) = listUsers[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemUsername.text = username
        holder.binding.tvItemWork.text = company
        holder.binding.tvItemLive.text = location
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Buka ${listUsers[holder.adapterPosition].name}", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUsers[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUsers.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}