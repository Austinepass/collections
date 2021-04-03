package com.example.task10.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task10.data.model.CommentItem
import com.example.task10.data.model.PostItem
import com.example.task10.data.PostRepository

class PostViewModel : ViewModel() {

    /**
     *[PostRepository] instance is created and initialised here.
     */
    private var postRepository = PostRepository()

    /**
     *LiveData variables are initialised here.
     */
    var postItemListLiveData : LiveData<ArrayList<PostItem>> = MutableLiveData()
    var createPostLiveData : LiveData<PostItem>? = MutableLiveData()
    var tempNewPostList = ArrayList<PostItem>()
    var tempNewCommentList = ArrayList<CommentItem>()

    var commentListLiveData : LiveData<ArrayList<CommentItem>> = MutableLiveData()
    var createCommentLiveData : LiveData<CommentItem> = MutableLiveData()


    /**
     *Network calls are made here via the [PostRepository] for the [getAllPosts],
     * [createPost], [getComments], and [createComment] functions
     */
    fun getAllPosts(){
        postItemListLiveData = postRepository.getPosts()
    }

    fun createPost(postItem: PostItem){
        createPostLiveData = postRepository.createPost(postItem)
        tempNewPostList.add(0, postItem)
    }

    fun getComments(position: Int) {
        val id = postItemListLiveData.value?.get(position)?.id!!
        commentListLiveData = postRepository.getComments(id)
    }

    fun createComment(commentItem: CommentItem, position: Int) {
        val postId = postItemListLiveData.value?.get(position)?.id!!
        createCommentLiveData = postRepository.createComment(commentItem, postId)
        tempNewCommentList.add(0, commentItem)
    }
}