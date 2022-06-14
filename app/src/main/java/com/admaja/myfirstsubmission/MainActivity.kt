package com.admaja.myfirstsubmission

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admaja.myfirstsubmission.models.Users

class MainActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private val list = ArrayList<Users>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUsers = findViewById(R.id.rv_profile)
        rvUsers.setHasFixedSize(true)

        list.addAll(listUsers)
        showRecyclerList()
    }

    private val listUsers: ArrayList<Users>get() {
        val dataName = resources.getStringArray(R.array.name)
        val dataUsername = resources.getStringArray(R.array.username)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataRepository = resources.getStringArray(R.array.repository)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataFollowers = resources.getStringArray(R.array.followers)
        val dataPhoto = resources.obtainTypedArray(R.array.avatar)
        val listUser = ArrayList<Users>()
        for (i in dataUsername.indices) {
            val user = Users(
                dataName[i],
                dataUsername[i],
                dataLocation[i],
                dataRepository[i],
                dataCompany[i],
                dataFollowers[i],
                dataFollowing[i],
                dataPhoto.getResourceId(i, -1))
            listUser.add(user)
        }
        return listUser
    }

    private fun showSelectedUsers(user: Users) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USERS, user)
        startActivity(intent)
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUsers.layoutManager = LinearLayoutManager(this)
        }
        val listUsersAdapter = ListUsersAdapter(list)
        rvUsers.adapter = listUsersAdapter

        listUsersAdapter.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedUsers(data)
            }
        })
    }
}