package com.example.week2taskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TextFragment : Fragment() {

    //To maintain modularity and reusability
    companion object {
        fun newInstance(): TextFragment {
            return TextFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val viewGroup: View = inflater.inflate(R.layout.fragment_text, container, false)
        val textView: TextView = viewGroup.findViewById(R.id.fragment_textview)

        /**
         * Gets the String bundled from the [SecondActivity] and assigns it to the
         * fragment's TextView
         */
        textView.text =
                if (arguments == null) {
                    "Empty back stack"
                } else {
                    "Position ${arguments?.getString("count_key")}"
                }

        return viewGroup
    }

}