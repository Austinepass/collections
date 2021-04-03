package com.example.task7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.task7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController : NavController
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set up view binding for this activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.fragment)


        /**
         * Set up my BottomNavigationBar with [NavController]
         */
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        }
    }