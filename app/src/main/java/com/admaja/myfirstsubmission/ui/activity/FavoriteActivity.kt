package com.admaja.myfirstsubmission.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.admaja.myfirstsubmission.R
import com.admaja.myfirstsubmission.data.Result
import com.admaja.myfirstsubmission.data.local.entity.FavoriteEntity
import com.admaja.myfirstsubmission.databinding.ActivityFavoriteBinding
import com.admaja.myfirstsubmission.ui.DetailViewModel
import com.admaja.myfirstsubmission.ui.DetailViewModelFactory
import com.admaja.myfirstsubmission.ui.adapter.FavoriteListAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var detailViewModelFactory: DetailViewModelFactory
    private lateinit var favoriteAdapter: FavoriteListAdapter
    private val favorite = ArrayList<FavoriteEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModelFactory = DetailViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels{
            detailViewModelFactory
        }
        showFavoriteList()
        detailViewModel.getFavoriteUser().observe(this) {result->
            if(result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }
                    is Result.Succes -> {
                        binding.progressBar2.visibility = View.GONE
                        val favoriteData = result.data
                        favoriteAdapter.submitList(favoriteData)
                    }
                    is  Result.Error -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(this, "Terjadi kesalahan ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
                val intent = Intent(this@FavoriteActivity, OptionsActivity::class.java)
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


    private fun showFavoriteList() {
        favoriteAdapter = FavoriteListAdapter(favorite)
        favoriteAdapter.notifyDataSetChanged()
        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = favoriteAdapter
        }
    }
}