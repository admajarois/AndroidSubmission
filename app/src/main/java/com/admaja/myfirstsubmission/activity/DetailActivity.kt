package com.admaja.myfirstsubmission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.admaja.myfirstsubmission.api.DetailResponse
import com.admaja.myfirstsubmission.api.ItemsItem
import com.admaja.myfirstsubmission.databinding.ActivityDetailBinding
import com.admaja.myfirstsubmission.ui.UserViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var userViewModel: UserViewModel
    companion object {
        const val EXTRA_USERS = "extra_users"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        val user = intent.getParcelableExtra<ItemsItem>(EXTRA_USERS) as ItemsItem
        userViewModel.detailUsers(user.login)
        userViewModel.detail.observe(this, { detail ->
            showLoading(false)
            supportActionBar?.title = detail.login
            setUserDetail(detail)
        })
        showLoading(true)
    }

    private fun setUserDetail(detail: DetailResponse) {
        binding.apply {
            tvName.text = detail.login
            tvFollowers.text = detail.followers.toString()
            tvFollowing.text = detail.following.toString()
            tvRepository.text = detail.publicRepos.toString()
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivPhoto)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                pbFollowers.visibility = View.VISIBLE
                pbName.visibility = View.VISIBLE
                pbFollowing.visibility = View.VISIBLE
                pbRepo.visibility = View.VISIBLE
                pbImg.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                pbFollowers.visibility = View.INVISIBLE
                pbName.visibility = View.INVISIBLE
                pbFollowing.visibility = View.INVISIBLE
                pbRepo.visibility = View.INVISIBLE
                pbImg.visibility = View.INVISIBLE
            }
        }
    }
}