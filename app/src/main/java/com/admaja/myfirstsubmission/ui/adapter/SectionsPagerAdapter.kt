package com.admaja.myfirstsubmission.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.admaja.myfirstsubmission.api.ItemsItem
import com.admaja.myfirstsubmission.ui.fragment.FollowersFragment
import com.admaja.myfirstsubmission.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data:Bundle): FragmentStateAdapter(activity) {
    private lateinit var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}