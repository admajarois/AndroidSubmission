package com.admaja.myfirstsubmission.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.admaja.myfirstsubmission.api.FollowersResponseItem
import com.admaja.myfirstsubmission.databinding.FragmentFollowBinding
import com.admaja.myfirstsubmission.ui.UserViewModel
import com.admaja.myfirstsubmission.ui.activity.DetailActivity
import com.admaja.myfirstsubmission.ui.adapter.FollowersAdapter

class FollowersFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentFollowBinding
    private lateinit var followersAdapter: FollowersAdapter
    private val list = ArrayList<FollowersResponseItem>()
    private lateinit var username:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERS).toString()
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        showRecycleList()
        userViewModel.setFollowers(username)
        userViewModel.followers.observe(viewLifecycleOwner, {
            if (it != null) {
                followersAdapter.setList(it)
            }
        })
        userViewModel.isLoading.observe(viewLifecycleOwner, {
            shoLoading(it)
        })
    }

    private fun showRecycleList() {
        followersAdapter = FollowersAdapter(list)
        followersAdapter.notifyDataSetChanged()
        binding.apply {
            rvFollow.layoutManager = LinearLayoutManager(context)
            rvFollow.setHasFixedSize(true)
            rvFollow.adapter = followersAdapter
        }
    }

    private fun shoLoading(state: Boolean) {
        if (state) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.INVISIBLE
        }
    }
}