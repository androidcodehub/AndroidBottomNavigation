package com.androidcodehub.myapplication

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout

import java.util.ArrayList

/**
 *
 */
class DemoFragment : Fragment() {

    private var fragmentContainer: FrameLayout? = null
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments.getInt("index", 0) == 0) {
            val view = inflater!!.inflate(R.layout.fragment_demo_settings, container, false)
            initDemoSettings(view)
            return view
        } else {
            val view = inflater!!.inflate(R.layout.fragment_demo_list, container, false)
            initDemoList(view)
            return view
        }
    }

    /**
     * Init demo settings
     */
    private fun initDemoSettings(view: View) {


    }

    /**
     * Init the fragment
     */
    private fun initDemoList(view: View) {

        fragmentContainer = view.findViewById<View>(R.id.fragment_container) as FrameLayout
        recyclerView = view.findViewById<View>(R.id.fragment_demo_recycler_view) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager

        val itemsData = ArrayList<String>()
        for (i in 0..49) {
            itemsData.add("Fragment " + arguments.getInt("index", -1) + " / Item " + i)
        }

        val adapter = DemoAdapter(itemsData)
        recyclerView!!.adapter = adapter
    }

    /**
     * Refresh
     */
    fun refresh() {
        if (arguments.getInt("index", 0) > 0 && recyclerView != null) {
            recyclerView!!.smoothScrollToPosition(0)
        }
    }

    /**
     * Called when a fragment will be displayed
     */
    fun willBeDisplayed() {
        // Do what you want here, for example animate the content
        if (fragmentContainer != null) {
            val fadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in)
            fragmentContainer!!.startAnimation(fadeIn)
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    fun willBeHidden() {
        if (fragmentContainer != null) {
            val fadeOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out)
            fragmentContainer!!.startAnimation(fadeOut)
        }
    }

    companion object {

        /**
         * Create a new instance of the fragment
         */
        fun newInstance(index: Int): DemoFragment {
            val fragment = DemoFragment()
            val b = Bundle()
            b.putInt("index", index)
            fragment.arguments = b
            return fragment
        }
    }
}
