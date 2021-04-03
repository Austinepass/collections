package com.example.task6

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task6.implement1.ContactListAdapter
import com.example.task6.implement1.ContactListFragment

/**
 * A simple [Fragment] subclass.
 * Use the [PhoneContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneContactsFragment : Fragment(), ContactListAdapter.OnItemClickListener {
    private var contactList = arrayListOf<ContactEntity>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_phone_contacts, container, false)
        recyclerView = view.findViewById(R.id.phone_contact_list_recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        /**
         *Checks if permission is already granted, and  displays the contacts saved in the phone
         */
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 1)
        } else getContacts()


        return view
    }

    /**
     *Querries the [ContactsContract] through the activity's contentResolver and return
     * the names and numbers associated with each contact and adds them to a the [contactList]
     */
    private fun getContacts(){
        val contacts = requireActivity().contentResolver
                ?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        while (contacts!!.moveToNext()) {
            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactEntity = ContactEntity("0", name, number)
            contactList.add(contactEntity)
        }

        recyclerView.adapter = ContactListAdapter(contactList, this)
        contacts.close()

    }

    /**
     * Depending on the  [grantResults], a READ_CONTENT action is started if persmission is granted
     * else, a toast message is displayed explaining the need for the permission
     */
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContacts()
        }else {
            onAlertDialog()
        }
    }

    override fun onItemClick(position: Int) {
        val callNumber = contactList[position].phone
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$callNumber")
        startActivity(callIntent)
    }

    //A simple AlertDialog Builder to properly respond to users permission choice
    private fun onAlertDialog() {
        //Instantiate builder variable
        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle("Permission denied!")

        builder.setMessage("You wont't be able to see your contact list. Grant permission now?")

        builder.setPositiveButton(
                "Grant") { _,_ ->
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 1)
        }

        builder.setNegativeButton(
                "Exit dialog") {_,_ -> }


        builder.show()
    }


}