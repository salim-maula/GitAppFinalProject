package com.salim.finalprojectgitappsalim.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.salim.finalprojectgitappsalim.fragment.FragmentFollower
import com.salim.finalprojectgitappsalim.fragment.FragmentFollowing

class PagerAdapter(activity: AppCompatActivity, data: Bundle) : FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle
    init {
        fragmentBundle = data
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FragmentFollower()
            1 -> fragment = FragmentFollowing()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}