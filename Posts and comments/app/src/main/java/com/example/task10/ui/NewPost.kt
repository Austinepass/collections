package com.example.task10.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.task10.R
import com.example.task10.data.model.PostItem
import com.example.task10.databinding.NewPostBinding
import com.example.task10.viewmodel.PostViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [NewPost.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewPost : Fragment() {
    private var _binding: NewPostBinding? = null
    lateinit var binding: NewPostBinding
    lateinit var viewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.new_post, container, false)
        binding = NewPostBinding.bind(view)
        _binding = binding
        viewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

        binding.submitPost.setOnClickListener {
            submitPost()
            hideKeyboard()
        }

        return binding.root
    }

    /**
     * Methods for creating a new post through the [PostViewModel] instance
     * via the PostRepository
     */

    private fun submitPost() {
        val body = binding.newPost.text.toString()
        val title = binding.newTitle.text.toString()
        val newPostItem = PostItem(body, 1, title, 1000)
        viewModel.createPost(newPostItem)
        findNavController().popBackStack()


    }

    private fun Fragment.hideKeyboard() {
        view?.let { requireActivity().hideKeyboard(it) }
    }
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Clean up view binding resources
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}