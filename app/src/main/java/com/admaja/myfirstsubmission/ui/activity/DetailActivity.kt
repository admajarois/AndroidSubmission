package com.admaja.myfirstsubmission.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.admaja.myfirstsubmission.R
import com.admaja.myfirstsubmission.data.Result
import com.admaja.myfirstsubmission.data.api.DetailResponse
import com.admaja.myfirstsubmission.data.api.ItemsItem
import com.admaja.myfirstsubmission.data.local.entity.FavoriteEntity
import com.admaja.myfirstsubmission.databinding.ActivityDetailBinding
import com.admaja.myfirstsubmission.ui.DetailViewModel
import com.admaja.myfirstsubmission.ui.DetailViewModelFactory
import com.admaja.myfirstsubmission.ui.UserViewModel
import com.admaja.myfirstsubmission.ui.adapter.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.circleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModelFactory: DetailViewModelFactory

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

        detailViewModelFactory = DetailViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            detailViewModelFactory
        }

        detailViewModel.getDetailUser(user.login).observe(this) {result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Succes -> {
                        showLoading(false)
                        val detailData = result.data
                        setUserDetail(detailData)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this,"Terjadi kesalahan ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val favoriteFab = binding.favoriteFab
        favoriteFab.setOnClickListener{
            Toast.makeText(this, "Tai asu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserDetail(detail: List<FavoriteEntity>) {
        detail.forEach{user ->
            binding.apply {
                tvName.text = if (user.name != null) {
                    user.name
                } else {
                    user.login
                }
                tvFollowers.text = user.followers.toString()
                tvFollowing.text = user.following.toString()
                tvRepository.text = user.repository.toString()
                Glide.with(this@DetailActivity)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPhoto)
            }
            supportActionBar?.title = user.login
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