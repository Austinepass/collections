package com.example.week2taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf

class SecondActivity : AppCompatActivity() {
    private val manager = supportFragmentManager

    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var count: String
    private lateinit var bundle: Bundle

    private val COUNT_KEY = "count_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        addButton = findViewById(R.id.add_fragment)
        removeButton = findViewById(R.id.remove_fragment)

        addButton.setOnClickListener {
            addFragment()
        }

        removeButton.setOnClickListener {
            removeTopFragment()
        }
    }

    /**
     * Creates an instance of the [TextFragment] and adds it
     * to the [manager]'s back stack.
     */
    private fun addFragment() {
        val textFragment = TextFragment.newInstance()
        val transaction = manager.beginTransaction()

        transaction.apply {
            add(R.id.fragment_container, textFragment)
            addToBackStack(null)
            commit()
        }

        //Bundles the backStackEntryCount to the TextFragment class
        count = (manager.backStackEntryCount + 1).toString()
        bundle = bundleOf(COUNT_KEY to count)
        textFragment.arguments = bundle
    }

    //Removes the topmost fragment from the back stack.
    private fun removeTopFragment() {
        manager.popBackStack()
    }

}