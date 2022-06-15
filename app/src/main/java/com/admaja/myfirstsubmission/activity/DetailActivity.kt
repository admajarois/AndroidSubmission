package com.admaja.myfirstsubmission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.admaja.myfirstsubmission.databinding.ActivityDetailBinding
import com.admaja.myfirstsubmission.models.Users
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val EXTRA_USERS = "extra_users"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        val user = intent.getParcelableExtra<Users>(EXTRA_USERS) as Users
//        supportActionBar!!.title = user.username
//        binding.tvName.text = user.name
//        binding.tvFollowers.text = "${user.followers} Followers"
//        binding.tvRepository.text = "${user.repository} Repository"
//        binding.tvFollowing.text = "${user.following} Following"
//        binding.tvWork.text = user.company
//        binding.tvLocation.text = user.location
//        Glide.with(binding.root)
//            .load(user.photo)
//            .circleCrop()
//            .into(binding.ivPhoto)
    }
}