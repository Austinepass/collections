package com.example.task6.implement1

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.task6.DB
import com.example.task6.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Use the [ContactDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactDetailsFragment : Fragment() {
    lateinit var id: String
    lateinit var callNumber: String
    lateinit var call: FloatingActionButton
    lateinit var share: ImageView
    lateinit var deleteButton: ImageView
    lateinit var name: TextView
    lateinit var number: TextView

    companion object {
        fun newInstance(): ContactDetailsFragment {
            return ContactDetailsFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact_details, container, false)

        /**
         * Get a reference to the various views **I will use databinding next time**
         */
        call = view.findViewById(R.id.call)
        share = view.findViewById(R.id.share)
        name = view.findViewById(R.id.name_details)
        number = view.findViewById(R.id.number_details)
        deleteButton = view.findViewById(R.id.delete_button)
        val button = view.findViewById<Button>(R.id.edit_button)
        val tb = view.findViewById<Toolbar>(R.id.details_toolbar)

        //Setup the back button of the ToolBar
        tb.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        /**
         * Hook a click listener to the call dialog_yes which will request for
         * permission and start the call intent if the permission is granted already
         */

        call.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 2)
            } else makeCall()
        }

        //Hook a click listener to the name dialog_yes
        name.text = arguments?.getString("name")

        //Hook a click listener to the number dialog_yes
        number.text = arguments?.getString("number")
        id = arguments?.getString("id")!!
        share.setOnClickListener {
            shareContact()
        }

        callNumber = "${number.text}"


        /**
         * Get the details from the [name] and [number] textfields and
         * bundles them to the [EditFragment]
         */
        button.setOnClickListener {
            val editFragment = EditFragment()
            val bundle = Bundle()

            bundle.putString("title", "Edit Contact")
            bundle.putString("edit_name", "${name.text}")
            bundle.putString("edit_number", "${number.text}")
            bundle.putString("id", id)
            editFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, editFragment)
                addToBackStack(null)
                commit()
            }

        }

        /**
        * Hook a click listener to the delete dialog_yes which deletes an entity
         * from the database with a given [id]
         */
        deleteButton.setOnClickListener {
            DB.child(id).removeValue()

            //Opens the ContactListFragment after deleting an entity from the DB
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                            R.id.fragment_container,
                            ContactListFragment()
                    )
                    ?.addToBackStack(null)?.commit()
        }
        return view
    }

    /**
     * Starts a call intent with the number from the number EditText field
     */
    private fun makeCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$callNumber")
        startActivity(callIntent)
    }

    /**
     * Starts the share intent with the name and number as intent extras
     */
    private fun shareContact() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Name: ${name.text}\n" +
                "Number: ${number.text}")
        shareIntent.type = "text/plain"
        startActivity(Intent.createChooser(shareIntent, "Share contact via"))

    }

    /**
     * Depending on the  [grantResults], a call intent is started if permission is granted
     * else, a toast message is displayed explaining the need for the permission
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makeCall()
        } else {
            onAlertDialog()
        }
    }

    //A simple AlertDialog Builder to properly respond to users'
    private fun onAlertDialog() {
        //Instantiate builder variable
        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle("Permission denied!")

        builder.setMessage("You wont't be able to make calls. Grant permission now?")

        builder.setPositiveButton(
                "Grant") { _,_ ->
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 2)
        }

        builder.setNegativeButton(
                "Exit dialog") {_,_ -> }


        builder.show()
    }

}