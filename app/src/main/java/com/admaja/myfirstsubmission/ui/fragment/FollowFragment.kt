package com.admaja.myfirstsubmission.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.admaja.myfirstsubmission.data.api.FollowResponseItem
import com.admaja.myfirstsubmission.databinding.FragmentFollowBinding
import com.admaja.myfirstsubmission.ui.UserViewModel
import com.admaja.myfirstsubmission.ui.activity.DetailActivity
import com.admaja.myfirstsubmission.ui.adapter.FollowAdapter

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private var tabName:String? = null
    private val list = ArrayList<FollowResponseItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(DetailActivity.EXTRA_USERS).toString()
        tabName = arguments?.getString(ARG_TAB)
        val userViewModel: UserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        val followAdapter = FollowAdapter(list)
        followAdapter.notifyDataSetChanged()
        binding?.apply {
            rvFollow?.layoutManager = LinearLayoutManager(context)
            rvFollow?.setHasFixedSize(true)
            rvFollow?.adapter = followAdapter
        }
        if (tabName == TAB_FOLLOWERS) {
            userViewModel.setFollowers(username)
            userViewModel.followers.observe(viewLifecycleOwner, {
                if (it != null) {
                    followAdapter?.setList(it)
                }
            })
        }else if (tabName == TAB_FOLLOWING) {
            userViewModel.setFollowing(username)
            userViewModel.following.observe(viewLifecycleOwner, {
                if (it != null) {
                    followAdapter?.setList(it)
                }
            })
        }
        userViewModel.isLoading.observe(viewLifecycleOwner, {
            shoLoading(it)
        })
    }

    private fun shoLoading(state: Boolean) {
        if (state) {
            binding?.pbLoading?.visibility = View.VISIBLE
        } else {
            binding?.pbLoading?.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_FOLLOWERS = "followers"
        const val TAB_FOLLOWING = "following"
    }
}