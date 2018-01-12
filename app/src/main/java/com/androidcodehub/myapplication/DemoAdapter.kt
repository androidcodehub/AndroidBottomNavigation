package com.androidcodehub.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androidcodehub.myapplication.DemoAdapter.ViewHolder

import java.util.ArrayList

/**
 *
 */
class DemoAdapter(dataset: ArrayList<String>) : RecyclerView.Adapter<DemoAdapter.ViewHolder>() {

    private val mDataset = ArrayList<String>()

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var mTextView: TextView

        init {
            mTextView = v.findViewById<View>(R.id.layout_item_demo_title) as TextView
        }
    }

    init {
        mDataset.clear()
        mDataset.addAll(dataset)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_demo, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextView.text = mDataset[position]

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

}
