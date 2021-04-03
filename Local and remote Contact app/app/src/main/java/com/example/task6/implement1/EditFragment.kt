package com.example.task6.implement1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.task6.*
import kotlinx.android.synthetic.main.fragment_edit.*

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    lateinit var id: String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        var editName = view.findViewById<EditText>(R.id.edit_name)
        val numberEdit = view.findViewById<EditText>(R.id.number_edit)

        val tb = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.edit_toolbar)
        tb.title = arguments?.getString("title")

        /**
         * Sets the title to 'Edit Contact' if the title value from the argument
         * states so since I'm using one fragment for the addition and editing
         * of contacts
         */
        if (tb.title == "Edit Contact") {
            editName.setText(arguments?.getString("edit_name"))
            numberEdit.setText(arguments?.getString("edit_number"))
            id = arguments?.getString("id")!!
        }

        val saveIcon = view.findViewById<ImageView>(R.id.save_icon)

        saveIcon.setOnClickListener {
            if (tb.title == "Edit Contact") {
                updateContactToDatabase()
            } else addContactToDatabase()
        }

        return view
    }

    /**
     * Creates a [ContactEntity] with details from the EditText fields and
     * uploads to the [DB] database
     */
    private fun addContactToDatabase() {
        val name = edit_name.text.toString()
        val number = number_edit.text.toString()
        val id = DB.push().key
        val contact = ContactEntity(id!!, name, number)

        when {
            !validName(name) -> edit_name.error = "Put a valid name"
            !validNumber(number) -> number_edit.error = "Put a valid number"
            else -> {

                DB.child(id).setValue(contact)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.fragment_container,
                        ContactListFragment()
                    )
                    ?.addToBackStack(null)?.commit()

            }
        }
    }

    /**
     * Updates a [ContactEntity] matching a given [id]and
     * uploads to the [DB] database
     */
    private fun updateContactToDatabase() {
        val name = edit_name.text.toString()
        val number = number_edit.text.toString()
        val contact = ContactEntity(id, name, number)

        when {
            !validName(name) -> edit_name.error = "Put a valid name"
            !validNumber(number) -> number_edit.error = "Put a valid number"
            else -> {

                DB.child(id).setValue(contact)
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(
                                R.id.fragment_container,
                                ContactListFragment()
                        )
                        ?.addToBackStack(null)?.commit()
            }
        }
    }

}