package com.admaja.myfirstsubmission.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.v
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.admaja.myfirstsubmission.R
import com.admaja.myfirstsubmission.api.DetailResponse
import com.admaja.myfirstsubmission.api.ItemsItem
import com.admaja.myfirstsubmission.databinding.ActivityDetailBinding
import com.admaja.myfirstsubmission.ui.UserViewModel
import com.admaja.myfirstsubmission.ui.adapter.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<ItemsItem>(EXTRA_USERS) as ItemsItem
        val bundle = Bundle()
        bundle.putString(EXTRA_USERS, user.login)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TILES[position])
        }.attach()
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        userViewModel.detailUsers(user.login)
        userViewModel.detail.observe(this, { detail ->
            supportActionBar?.title = detail.login
            setUserDetail(detail)
        })
        userViewModel.isLoading.observe(this,{
            showLoading(it)
        })
    }

    private fun setUserDetail(detail: DetailResponse) {
        binding.apply {
            tvName.text = if (detail.name != null) {
                detail.name.toString()
            } else {
                detail.login
            }
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
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

    companion object {
        const val EXTRA_USERS = "extra_users"
        @StringRes
        private val TAB_TILES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}