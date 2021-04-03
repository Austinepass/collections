package com.example.week_4_group_task_ta_droid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.DateFormat
import java.util.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        /**
         * Creates and sets instances of the [Calendar] and [DateFormat] classes
         * to the date_textView and time_textView TextViews
         */
        val calendar = Calendar.getInstance().time
        val currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        val currentTime = DateFormat.getTimeInstance().format(calendar.time)
        val date: TextView = findViewById(R.id.date_textView)
        date.text = currentDate
        val time: TextView = findViewById(R.id.time_textView)
        time.text = currentTime
    }
}