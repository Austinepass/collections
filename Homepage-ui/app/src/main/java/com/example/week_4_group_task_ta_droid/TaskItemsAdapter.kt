package com.example.week_4_group_task_ta_droid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * An adapter class for the task items
 * @param context activity in which the adapter is to be used
 * @param tasks lists containing items to be displayed
 */
class TaskItemsAdapter(private val context: Context, private val tasks : List<Tasks>) : RecyclerView.Adapter<TaskItemsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
         val imageTask: ImageView = view.findViewById(R.id.imgTask)
         val txtTask: TextView = view.findViewById(R.id.txtTask)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.task_items_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageTask.setImageResource(tasks[position].imgId)
        holder.txtTask.text = tasks[position].txtTask

    }

    override fun getItemCount() = tasks.size

}