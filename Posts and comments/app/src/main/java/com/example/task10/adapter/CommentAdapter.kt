package com.example.task10.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task10.R
import com.example.task10.data.model.CommentItem

class CommentAdapter(val comment: ArrayList<CommentItem>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val email = v.findViewById<TextView>(R.id.email_text)
        val comment = v.findViewById<TextView>(R.id.comment_body)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = comment.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.email.text = comment[position].email
        holder.comment.text = comment[position].body
    }

    fun addComment(list: ArrayList<CommentItem>) {
        comment.addAll(0,list)
        notifyItemInserted(0)
        list.clear()
    }
}