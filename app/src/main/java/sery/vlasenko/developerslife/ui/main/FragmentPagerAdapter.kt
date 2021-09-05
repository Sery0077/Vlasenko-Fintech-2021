package sery.vlasenko.developerslife.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import sery.vlasenko.developerslife.ui.randomGif.RandomGifFragment

class FragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment = RandomGifFragment()
}