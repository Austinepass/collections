package com.example.task10.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task10.adapter.PostAdapter
import com.example.task10.R
import com.example.task10.data.model.PostItem
import com.example.task10.databinding.PostListBinding
import com.example.task10.viewmodel.PostViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [PostList.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostList : Fragment(), PostAdapter.OnItemClickListener {
    private var _binding: PostListBinding? = null
    lateinit var binding: PostListBinding
    lateinit var viewModel: PostViewModel
    lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.post_list, container, false)
        binding = PostListBinding.bind(view)
        _binding = binding

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PostAdapter(this)
        binding.recyclerView.adapter = adapter

        /**
         * Instantiate [PostViewModel] and make the api call to fetch the list of posts
         * via the repository
         */
        viewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
        displayList()


        /**
         * FAB opens up a new post fragment page
         */
        binding.floatingActionButton.setOnClickListener {
            val action = PostListDirections.actionPostListToNewPost()
            findNavController().navigate(action)
        }


        return binding.root
    }


    /**
     * Method for making network call via the [PostViewModel] instance via the Repository.
     * The list is gotten is observed and assigned to the view component that needs it here.
     * A TextChangedListener is also added here to filter the list based on the query from the
     * search EditText field
     */

    private fun displayList() {
        viewModel.getAllPosts()
            viewModel.postItemListLiveData.observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
                if (viewModel.createPostLiveData?.value != null) {
                    adapter.addData(viewModel.tempNewPostList)
                }

                binding.searchBar.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        search(s.toString(), it)
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                })
            })



    }

    /**
     * Clean up view binding instance
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Function for searching for a particular post based on the characters
     * of the post's title or body
     */
    private fun search(s: String, list: ArrayList<PostItem>) {
        var filtered = arrayListOf<PostItem>()
        for (i in list) {
            if(s in i.body || s in i.title ) {
                filtered.add(i)
            }
        }
        adapter.setData(filtered)
        adapter.notifyDataSetChanged()
    }

    /**
     * Handles the onClick event on the recyclerView list item which opens up
     * the [PostPage] with values of each item as arguments
     */
    override fun onItemClick(position: Int) {
        val action = PostListDirections.actionPostListToPostPage(position)
        findNavController().navigate(action)
    }

}