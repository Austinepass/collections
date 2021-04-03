package com.example.task6

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.task6.implement1.ContactListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         *Create instances of the [ContactListFragment] and the [PhoneContactsFragment]
         */
        val contactListFragment = ContactListFragment()
        val phoneContactsFragment = PhoneContactsFragment()

        replaceFragment(contactListFragment)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        /**
         *Navigates between the [ContactListFragment] and the [PhoneContactsFragment]
         */
        bottomNav.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.homie -> {
                    replaceFragment(ContactListFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    replaceFragment(phoneContactsFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }

    /**
     *Helper methods for replacing fragments
     */
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
}