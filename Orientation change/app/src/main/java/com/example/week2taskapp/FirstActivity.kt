package com.example.week2taskapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FirstActivity : AppCompatActivity() {

    //TextViews for displaying the state and orientation changes
    private lateinit var stateTextView: TextView
    private lateinit var orientationTextView: TextView

    //To track orientation changes
    companion object {
        private var count = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        stateTextView = findViewById(R.id.state_textview)

        stateTextView.text = getString(R.string.created)
        orientationTextView = findViewById(R.id.orientation_textview)

        onConfigChanged()

    }

    /**
     * Checks the orientation of the screen and displays it on the
     * [orientationTextView] while keeping track of the number of
     * orientation changes
     */
    private fun onConfigChanged() {
        val currentOrientation = this.resources.configuration.orientation

        orientationTextView.text = if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            "Landscape Mode, rotated $count times"
        } else {
            "Portrait Mode, rotated $count times"
        }
        count++
    }

    /**
     * Overrides the callback methods for the various activity lifecycle
     * states and set the corresponding [stateTextView] TextView messages.
     */
    override fun onPause() {
        stateTextView.text = getString(R.string.paused)
        super.onPause()
    }


    override fun onRestart() {
        stateTextView.text = getString(R.string.restarted)
        super.onRestart()
    }

    override fun onStart() {
        stateTextView.text = getString(R.string.started)
        super.onStart()
    }

    override fun onStop() {
        stateTextView.text = getString(R.string.stopped)
        super.onStop()
    }

    override fun onDestroy() {
        stateTextView.text = getString(R.string.destroyed)
        super.onDestroy()
    }

    override fun onResume() {
        stateTextView.text = getString(R.string.resumed)
        super.onResume()
    }

}