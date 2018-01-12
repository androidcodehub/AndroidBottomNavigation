package com.androidcodehub.myapplication

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

import java.util.ArrayList

/**
 *
 */
class DemoViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = ArrayList<DemoFragment>()
    /**
     * Get the current fragment
     */
    var currentFragment: DemoFragment? = null
        private set

    init {

        fragments.clear()
        fragments.add(DemoFragment.newInstance(0))
        fragments.add(DemoFragment.newInstance(1))
        fragments.add(DemoFragment.newInstance(2))
        fragments.add(DemoFragment.newInstance(3))
        fragments.add(DemoFragment.newInstance(4))
    }

    override fun getItem(position: Int): DemoFragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any) {
        if (currentFragment !== `object`) {
            currentFragment = `object` as DemoFragment
        }
        super.setPrimaryItem(container, position, `object`)
    }
}