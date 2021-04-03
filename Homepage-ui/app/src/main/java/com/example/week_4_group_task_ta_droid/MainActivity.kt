package com.example.week_4_group_task_ta_droid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_first.setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }

        btn_second.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}