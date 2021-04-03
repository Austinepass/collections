package com.example.week2taskapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonOne = findViewById(R.id.implementation_one)
        buttonTwo = findViewById(R.id.implementation_two)

        //Opens the activity of the first implementation.
        buttonOne.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }

        //Opens the activity of the second implementation.

        buttonTwo.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }


}