package com.example.task10.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task10.R
import com.example.task10.adapter.CommentAdapter
import com.example.task10.databinding.PostPageBinding
import com.example.task10.viewmodel.PostViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [PostPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostPage : Fragment() {
    var _binding: PostPageBinding? = null
    private lateinit var binding: PostPageBinding
    private val viewModel: PostViewModel by activityViewModels()

    private val args: PostPageArgs by navArgs()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.post_page, container, false)
        binding = PostPageBinding.bind(view)
        _binding = binding

        /**
         * Fetch comments from the api
         */
//        if (viewModel.postItemListLiveData.value?.get(args.position) !in viewModel.tempNewPostList) {
//            viewModel.getComments(args.position)
//        }

        binding.commentList.layoutManager = LinearLayoutManager(requireContext())

        /**
         * PostItemListLiveData is observed here.
         */
        viewModel.postItemListLiveData.observe(viewLifecycleOwner, Observer {
            binding.bodyTitle.text = it[args.position].title
            binding.body.text = it[args.position].body
            if (it[args.position] !in viewModel.tempNewPostList) {
                viewModel.getComments(args.position)
            }
        })

        observeComment()
        /**
         * Comment button opens up the comment dialog box.
         */
        binding.commentButton.setOnClickListener {
            val action = PostPageDirections.actionPostPageToCommentPage(args.position)
            findNavController().navigate(action)
        }

        return binding.root
    }

    /**
     * CommentListLiveData is observed here.
     */
    private fun observeComment() {
        viewModel.commentListLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = CommentAdapter(it)
            adapter.notifyDataSetChanged()
            binding.commentList.adapter = adapter
            if (viewModel.commentListLiveData != null) {
                adapter.addComment(viewModel.tempNewCommentList)
                adapter.notifyDataSetChanged()
            }

            binding.commentCount.text = "Comments (${it.size})"
        })

    }


    /**
     * Clean up view binding resources.
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}