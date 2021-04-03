package com.example.task6.implement1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task6.ContactEntity
import com.example.task6.R

class ContactListAdapter(var contactList: ArrayList<ContactEntity>,
                         private val listener: OnItemClickListener
) : RecyclerView.Adapter<ContactListAdapter.ContactListAdapterViewHolder>() {


    inner class ContactListAdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nameText: TextView = v.findViewById(R.id.name_item)
        val numberText: TextView = v.findViewById(R.id.number_item)

        //Passes the adapter position to the OnItemListener onItemClick() method
        init {
            v.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ContactListAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        return ContactListAdapterViewHolder(view)
    }

    override fun getItemCount() = contactList.size

    override fun onBindViewHolder(holder: ContactListAdapterViewHolder, position: Int) {
        val contacts = contactList[position]
        holder.nameText.text = contacts.fullname
        holder.numberText.text = contacts.phone
    }
    /**
     * interface for the click event on the recyclerview items
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int)

    }
}