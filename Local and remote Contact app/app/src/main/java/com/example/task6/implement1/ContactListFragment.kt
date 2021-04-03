package com.example.task6.implement1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task6.ContactEntity
import com.example.task6.DB
import com.example.task6.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * A simple [Fragment] subclass.
 * Use the [ContactListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactListFragment : Fragment(), ContactListAdapter.OnItemClickListener {
    var contacts = arrayListOf<ContactEntity>()

    // private var numberArray = arrayListOf<String>()
    private lateinit var name: String
    private lateinit var number: String
    private lateinit var contact: ContactEntity
    private lateinit var mAdapter: ContactListAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)
        val recycler: RecyclerView = view.findViewById(R.id.contact_list_recycler)

        /**
         * FAB dialog_yes opens the 'Add Contact Instance' of the [EditFragment]
         */
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val addFragment = EditFragment()
            val bundle = Bundle()

            //Used to change the title of the EditFragment
            bundle.putString("title", "Add Contact")
            addFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, addFragment)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
                commit()
            }
        }

        /**
         * Listens to changes in the firebase's [DB] and propagates them
         * through the adapter
         */
        var getData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    name = i.child("fullname").value.toString()
                    number = i.child("phone").value.toString()
                    contact =
                            ContactEntity(i.key!!, name, number)
                    contacts.add(contact)
                    mAdapter = ContactListAdapter(
                            contacts,
                            this@ContactListFragment
                    )
                    recycler.adapter = mAdapter

                }

            }

        }
        DB.addValueEventListener(getData)



        recycler.layoutManager = LinearLayoutManager(activity)


        return view
    }

    /**
     * Opens and populates the  [ContactDetailsFragment] with the details from
     * the recyclerview's current adapter position
     */
    override fun onItemClick(position: Int) {
        val detailsFragment = ContactDetailsFragment.newInstance()
        val bundle = Bundle()
        bundle.putString("name", mAdapter.contactList[position].fullname)
        bundle.putString("number", mAdapter.contactList[position].phone)
        bundle.putString("id", mAdapter.contactList[position].id)
        detailsFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, detailsFragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()
        }
    }
}
