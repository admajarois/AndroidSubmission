package com.admaja.myfirstsubmission.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.admaja.myfirstsubmission.ui.activity.DetailActivity
import com.admaja.myfirstsubmission.ui.fragment.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data: Bundle): FragmentStateAdapter(activity) {
    private var fragmentBundle: Bundle
    init {
        fragmentBundle = data
    }
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        when(position) {
            0 -> fragmentBundle.putString(FollowFragment.ARG_TAB, FollowFragment.TAB_FOLLOWERS)
            1 -> fragmentBundle.putString(FollowFragment.ARG_TAB, FollowFragment.TAB_FOLLOWING)
        }
        fragment.arguments = fragmentBundle
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }

}