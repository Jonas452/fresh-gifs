package com.jonas.freshgifs.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jonas.freshgifs.R
import com.jonas.freshgifs.ui.favorite.FavoriteFragment
import com.jonas.freshgifs.ui.trending.TrendingFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_trending,
    R.string.tab_favorite
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TrendingFragment.newInstance()
            1 -> FavoriteFragment.newInstance()
            else -> throw IllegalArgumentException("Fragment for position $position doest not exist")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}
