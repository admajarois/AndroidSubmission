package com.admaja.myfirstsubmission.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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
    private var isFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<ItemsItem>(EXTRA_USERS)
        val favorite = intent.getParcelableExtra<FavoriteEntity>(EXTRA_FAVORITE)
        val login = user?.login?: favorite?.login
        val id = user?.id?: favorite?.id
        val avatarUrl = user?.avatarUrl?:favorite?.avatarUrl
        val bundle = Bundle()
        bundle.putString(EXTRA_USERS, login)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TILES[position])
        }.attach()

        detailViewModelFactory = DetailViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            detailViewModelFactory
        }
        detailViewModel.detailUsers(login)

        detailViewModel.detail.observe(this) {result ->
            showLoading(false)
            setUserDetail(result)
        }
        val favoriteFab = binding.favoriteFab

        detailViewModel.isFavorited(id).observe(this) { isFavorite ->
            if (isFavorite != null) {
                when(isFavorite) {
                    is Result.Loading -> {
                        Log.d("TAG", "Loading")
                    }
                    is Result.Succes -> {
                        val favorited = isFavorite.data
                        if (favorited) {
                            favoriteFab.setImageDrawable(ContextCompat.getDrawable(favoriteFab.context, R.drawable.ic_favorite))
                            isFavorited = true
                        } else {
                            favoriteFab.setImageDrawable(ContextCompat.getDrawable(favoriteFab.context, R.drawable.ic_favorite_outline))
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(this, "Terjadi kesalahan ${isFavorite.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        favoriteFab.setOnClickListener{
            if (isFavorited) {
                detailViewModel.deleteUser(id)
                Toast.makeText(this, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show()
            }else {
                detailViewModel.saveUser(FavoriteEntity(
                    id,
                    login,
                    avatarUrl
                ))
                Toast.makeText(this, "Berhasil difavoritkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.search).setVisible(false)
        menu.findItem(R.id.favorite).setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.dark_mode -> {
                val intent = Intent(this@DetailActivity, OptionsActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return true
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setUserDetail(detail: DetailResponse) {
        binding.apply {
            tvName.text = if (detail.name != null) detail.name.toString() else detail.login
            tvFollowers.text = detail.followers.toString()
            tvFollowing.text = detail.following.toString()
            tvRepository.text = detail.publicRepos.toString()
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivPhoto)
        }
        supportActionBar?.title = detail.login
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        const val EXTRA_FAVORITE = "extra_favorite"
        @StringRes
        private val TAB_TILES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}