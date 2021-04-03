package com.example.task5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity() {

    private val NAME_KEY = "name"
    private val PHONE_KEY = "phone"
    private val EMAIL_KEY = "email"
    private val SEX_KEY = "sex"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        /**
         * Get the intent extras passed from the [MainActivity], and
         * assign the values to the corresponding TextViews
         */
        val bundle = intent.extras
        name_text_view.text = "Name: ${bundle?.getString(NAME_KEY)}"
        phone_text_view.text = "Number: ${bundle?.getString(PHONE_KEY)}"
        email_text_view.text = "Email: ${bundle?.getString(EMAIL_KEY)}"
        sex_text_view.text = "Sex: ${bundle?.getString(SEX_KEY)}"

    }
}