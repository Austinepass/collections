package com.example.task10.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.task10.R
import com.example.task10.data.model.CommentItem
import com.example.task10.databinding.CommentPageBinding
import com.example.task10.viewmodel.PostViewModel


class CommentPage : Fragment() {
    private var _binding: CommentPageBinding? = null
    private lateinit var binding: CommentPageBinding
//    private lateinit var viewModel: PostViewModel
private val viewModel: PostViewModel by activityViewModels()
    private val args: CommentPageArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * Inflate the layout for this fragment
          */
        binding =  CommentPageBinding.bind(inflater.inflate(R.layout.comment_page, container, false))
        _binding = binding

        /**
         * Instantiate [PostViewModel] using the ViewModelProvider
         */
//        viewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)


        binding.submitComment.setOnClickListener {
            submitComment()
            hideKeyboard()
        }

        return binding.root
    }

    /**
     * Create comment with values from the comment EditText fields if they
     * are not empty.
     * Navigates back to the page fragment.
     */
    private fun submitComment() {
        var email = binding.commentEmail.text.toString().trim()
        var body = binding.commentBody.text.toString().trim()

        if (email.isNotEmpty() && body.isNotEmpty()) {
            val comment = CommentItem(body, email, 1, "name", 101)

            viewModel.createComment(comment, viewModel.postItemListLiveData.value?.get(args.position)?.id!!)
            val action = CommentPageDirections.actionCommentPageToPostPage(args.position)
            findNavController().navigate(action)
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { requireActivity().hideKeyboard(it) }
    }
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

        /**
         * Clean up view binding resource
         */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}