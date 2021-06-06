package com.example.traveler.fragments

import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveler.DEFAULT_TAB_COUNT
import com.example.traveler.PhotoItemDto
import com.example.traveler.R
import com.example.traveler.adapters.MyViewPagerAdapter
import com.example.traveler.database.Shared
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.home_layout.view.*
import kotlin.concurrent.thread

class HomeFragment : Fragment() {
    var myViewPagerAdapter: MyViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_layout, container, false).apply {
            initView(this)
        }
    }

    private fun initView(rootView: View){
        rootView.apply {
            myViewPagerAdapter = MyViewPagerAdapter(this@HomeFragment)
            with(viewPager) {
                adapter = myViewPagerAdapter
                offscreenPageLimit = 2
            }
            val headers = resources.getStringArray(R.array.tabsArray)
            TabLayoutMediator(tabLayout, viewPager){ tab: TabLayout.Tab, position: Int ->
                tab.text = headers[position]
            }.attach()
        }
    }
}