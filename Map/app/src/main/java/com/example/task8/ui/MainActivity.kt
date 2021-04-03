package com.example.task8.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.task8.R
import com.example.task8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.GONE

        //Set up view binding for this activity
        navController = Navigation.findNavController(this,
            R.id.fragment
        )

        /**
         * Set up my BottomNavigationBar with [NavController]
         */
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)


    }
}