package com.example.week_4_group_task_ta_droid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * An adapter class for the twitterFeeds
 * @param context activity in which the adapter is to be used
 * @param itemsCount number of items to be displayed
 */
class TwitterFeedsAdapter(private val context: Context, private val itemsCount : Int) : RecyclerView.Adapter<TwitterFeedsAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.twitter_feeds_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount() = itemsCount

}