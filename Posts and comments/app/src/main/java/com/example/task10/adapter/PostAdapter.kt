package com.example.task10.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task10.R
import com.example.task10.data.model.PostItem

class PostAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title = v.findViewById<TextView>(R.id.post_title)
        val content = v.findViewById<TextView>(R.id.post_content)

        init {
            v.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    listener.onItemClick(position)
                }
            }
        }
    }

    /**
     * Method for setting the list for the adapter
     */
    private var post = ArrayList<PostItem>()
    fun setData(list: ArrayList<PostItem>){
        post = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Method for inserting a new data into the adapter list and notifying the adapter
     */
    fun addData(postItem: ArrayList<PostItem>) {
        Log.i("Before", post.size.toString())
        post.addAll(0,postItem)
        Log.i("After", post.size.toString())
        notifyItemInserted(0)
    }

    override fun getItemCount() = post.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = post[position].title.toUpperCase()
        holder.content.text = post[position].body.capitalize()
    }

    /**
     * Interface for the adapter list items
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}