package com.example.traveler.adapters

import android.graphics.Bitmap
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.traveler.DEFAULT_TAB_COUNT
import com.example.traveler.PhotoItemDto
import com.example.traveler.fragments.LibraryFragment
import com.example.traveler.fragments.SettingsFragment

class MyViewPagerAdapter internal constructor(
    fm: Fragment
) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = DEFAULT_TAB_COUNT

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> LibraryFragment.newInstance()
        else -> SettingsFragment.newInstance()
    }
}