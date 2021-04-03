package com.example.task5

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    lateinit var item: String

    private val NAME_KEY = "name"
    private val PHONE_KEY = "phone"
    private val EMAIL_KEY = "email"
    private val SEX_KEY = "sex"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = name_editText.text
        val phone = phone_editText.text
        val email = email_editText.text

        val sexArray = arrayOf("N/A", "Male", "Female")

        //Spinner adapter
        val spinnerAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, sexArray)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //Get the item selected at any position
                item = parent?.getItemAtPosition(position).toString()
            }
        }

        button.setOnClickListener {
            val intent = Intent(this, SummaryActivity::class.java)

            when {

                /**
                 * Validity checks for the name, email, and phone number EditText views
                 */
                name.isEmpty() -> name_editText.error = "Put a name"
                !validEmail("$email") -> email_editText.error = "Put a valid email"
                !validNumber("$phone") -> phone_editText.error = "invalid number"

                else -> {
                    /**
                     *  Pass the values from the name, email, and phone number EditText views
                     *  as a bundle to the [SummaryActivity]
                     */

                    val bundle = Bundle()
                    bundle.apply {
                        putString(NAME_KEY, "$name")
                        putString(PHONE_KEY, "$phone")
                        putString(EMAIL_KEY, "$email")
                        putString(SEX_KEY, item)
                    }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
        }
    }



}