package com.androidcodehub.myapplication

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.View
import android.view.animation.OvershootInterpolator

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager
import com.aurelhubert.ahbottomnavigation.notification.AHNotification

import java.util.ArrayList

class DemoActivity : AppCompatActivity() {

    private var currentFragment: DemoFragment? = null
    private var adapter: DemoViewPagerAdapter? = null
    private var navigationAdapter: AHBottomNavigationAdapter? = null
    private val bottomNavigationItems = ArrayList<AHBottomNavigationItem>()
    private val useMenuResource = true
    private var tabColors: IntArray? = null
    private val handler = Handler()

    // UI
    private var viewPager: AHBottomNavigationViewPager? = null
    private var bottomNavigation: AHBottomNavigation? = null
    private var floatingActionButton: FloatingActionButton? = null

    /**
     * Return the number of items in the bottom navigation
     */
    val bottomNavigationNbItems: Int
        get() = bottomNavigation!!.itemsCount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val enabledTranslucentNavigation = getSharedPreferences("shared", Context.MODE_PRIVATE)
                .getBoolean("translucentNavigation", false)
        setTheme(if (enabledTranslucentNavigation) R.style.AppTheme_TranslucentNavigation else R.style.AppTheme)
        setContentView(R.layout.activity_home)
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * Init UI
     */
    private fun initUI() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        bottomNavigation = findViewById<View>(R.id.bottom_navigation) as AHBottomNavigation
        viewPager = findViewById<View>(R.id.view_pager) as AHBottomNavigationViewPager
        floatingActionButton = findViewById<View>(R.id.floating_action_button) as FloatingActionButton


        bottomNavigation!!.isColored = true


        if (useMenuResource) {
            tabColors = applicationContext.resources.getIntArray(R.array.tab_colors)
            navigationAdapter = AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu_5)
            navigationAdapter!!.setupWithBottomNavigation(bottomNavigation, tabColors)
        } else {
            val item1 = AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_apps_black_24dp, R.color.color_tab_1)
            val item2 = AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_maps_local_bar, R.color.color_tab_2)
            val item3 = AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_maps_local_restaurant, R.color.color_tab_3)
            val item4 = AHBottomNavigationItem(R.string.tab_4, R.drawable.ic_content_add, R.color.color_tab_4)
            val item5 = AHBottomNavigationItem(R.string.tab_5, R.drawable.ic_maps_local_attraction, R.color.color_tab_5)
            bottomNavigationItems.add(item1)
            bottomNavigationItems.add(item2)
            bottomNavigationItems.add(item3)
            bottomNavigationItems.add(item4)
            bottomNavigationItems.add(item5)

            bottomNavigation!!.addItems(bottomNavigationItems)
        }

        bottomNavigation!!.manageFloatingActionButtonBehavior(floatingActionButton!!)
        bottomNavigation!!.isTranslucentNavigationEnabled = true

        bottomNavigation!!.setOnTabSelectedListener(AHBottomNavigation.OnTabSelectedListener { position, wasSelected ->
            if (currentFragment == null) {
                currentFragment = adapter!!.currentFragment
            }

            if (wasSelected) {
                currentFragment!!.refresh()
                return@OnTabSelectedListener true
            }

            if (currentFragment != null) {
                currentFragment!!.willBeHidden()
            }

            viewPager!!.setCurrentItem(position, false)

            if (currentFragment == null) {
                return@OnTabSelectedListener true
            }

            currentFragment = adapter!!.currentFragment
            currentFragment!!.willBeDisplayed()

            if (position == 1) {
                bottomNavigation!!.setNotification("", 1)

                floatingActionButton!!.visibility = View.VISIBLE
                floatingActionButton!!.alpha = 0f
                floatingActionButton!!.scaleX = 0f
                floatingActionButton!!.scaleY = 0f
                floatingActionButton!!.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .setInterpolator(OvershootInterpolator())
                        .setListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {

                            }

                            override fun onAnimationEnd(animation: Animator) {
                                floatingActionButton!!.animate()
                                        .setInterpolator(LinearOutSlowInInterpolator())
                                        .start()
                            }

                            override fun onAnimationCancel(animation: Animator) {

                            }

                            override fun onAnimationRepeat(animation: Animator) {

                            }
                        })
                        .start()

            } else {
                if (floatingActionButton!!.visibility == View.VISIBLE) {
                    floatingActionButton!!.animate()
                            .alpha(0f)
                            .scaleX(0f)
                            .scaleY(0f)
                            .setDuration(300)
                            .setInterpolator(LinearOutSlowInInterpolator())
                            .setListener(object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator) {

                                }

                                override fun onAnimationEnd(animation: Animator) {
                                    floatingActionButton!!.visibility = View.GONE
                                }

                                override fun onAnimationCancel(animation: Animator) {
                                    floatingActionButton!!.visibility = View.GONE
                                }

                                override fun onAnimationRepeat(animation: Animator) {

                                }
                            })
                            .start()
                }
            }

            true
        })



        viewPager!!.offscreenPageLimit = 4
        adapter = DemoViewPagerAdapter(supportFragmentManager)
        viewPager!!.adapter = adapter

        currentFragment = adapter!!.currentFragment

        handler.postDelayed({
            // Setting custom colors for notification
            val notification = AHNotification.Builder()
                    .setText(":)")
                    .setBackgroundColor(ContextCompat.getColor(this@DemoActivity, R.color.color_notification_back))
                    .setTextColor(ContextCompat.getColor(this@DemoActivity, R.color.color_notification_text))
                    .build()
            bottomNavigation!!.setNotification(notification, 1)
            Snackbar.make(bottomNavigation!!, "Snackbar with bottom navigation",
                    Snackbar.LENGTH_SHORT).show()
        }, 3000)

        //bottomNavigation.setDefaultBackgroundResource(R.drawable.bottom_navigation_background);
    }

    /**
     * Update the bottom navigation colored param
     */


    /**
     * Show or hide the bottom navigation with animation
     */
    fun showOrHideBottomNavigation(show: Boolean) {
        if (show) {
            bottomNavigation!!.restoreBottomNavigation(true)
        } else {
            bottomNavigation!!.hideBottomNavigation(true)
        }
    }

    /**
     * Show or hide selected item background
     */
    fun updateSelectedBackgroundVisibility(isVisible: Boolean) {
        bottomNavigation!!.setSelectedBackgroundVisible(isVisible)
    }

    /**
     * Show or hide selected item background
     */
    fun setForceTitleHide(forceTitleHide: Boolean) {
        val state = if (forceTitleHide) AHBottomNavigation.TitleState.ALWAYS_HIDE else AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigation!!.titleState = state
    }

    /**
     * Reload activity
     */
    fun reload() {
        startActivity(Intent(this, DemoActivity::class.java))
        finish()
    }

}
