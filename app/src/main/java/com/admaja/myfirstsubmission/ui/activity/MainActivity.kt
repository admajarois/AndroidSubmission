package com.admaja.myfirstsubmission.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.admaja.myfirstsubmission.R
import com.admaja.myfirstsubmission.ui.adapter.ListUsersAdapter
import com.admaja.myfirstsubmission.data.api.ItemsItem
import com.admaja.myfirstsubmission.databinding.ActivityMainBinding
import com.admaja.myfirstsubmission.ui.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel:UserViewModel
    private lateinit var userAdapter: ListUsersAdapter
    private val list = ArrayList<ItemsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        showRecyclerList()
        userViewModel.user.observe(this, {
            if (it!=null) {
                userAdapter.setList(it)
            }
        })
        userViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        userViewModel.noData.observe(this, {
            showNoDataText(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                showNoDataText(false)
                userViewModel.setSearchUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.dark_mode -> {
                val intent = Intent(this@MainActivity, OptionsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.favorite -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
    }

    private fun showRecyclerList() {
        userAdapter = ListUsersAdapter(list)
        userAdapter.notifyDataSetChanged()
        binding.apply {
            rvProfile.layoutManager = LinearLayoutManager(this@MainActivity)
            rvProfile.setHasFixedSize(true)
            rvProfile.adapter = userAdapter
        }
        userAdapter.setItemClickCallback(object : ListUsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }

        })

    }

    private fun showSelectedUser(data: ItemsItem) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USERS, data)
        startActivity(intent)
    }

    private fun showNoDataText(noData: Boolean) {
        if(noData) {
            binding.tvNoItem.visibility = View.VISIBLE
        }else {
            binding.tvNoItem.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}