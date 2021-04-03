package com.example.task10.data

import androidx.lifecycle.MutableLiveData
import com.example.task10.data.model.CommentItem
import com.example.task10.data.model.PostItem
import com.example.task10.network.ApiRequest
import com.example.task10.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRepository  {
    /**
     * Instantiate [RetrofitClient] instance
     */
    private val retrofit = RetrofitClient.instance
    private var api : ApiRequest = retrofit.create(ApiRequest::class.java)

    /**
     * Network call for getting a list of posts from the provided api
     */
    fun getPosts(): MutableLiveData<ArrayList<PostItem>> {
        val data = MutableLiveData<ArrayList<PostItem>>()

        api.getPost().enqueue(object : Callback<ArrayList<PostItem>> {
            override fun onFailure(call: Call<ArrayList<PostItem>>, t: Throwable) {
                data.value = null
            }
            override fun onResponse(
                call: Call<ArrayList<PostItem>>,
                response: Response<ArrayList<PostItem>>
            ) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }
    /**
     * Network call for creating a new post and returning the created post
     */
    fun createPost(postItem: PostItem): MutableLiveData<PostItem> {
        val data = MutableLiveData<PostItem>()

        api.sendPosts(postItem).enqueue(object : Callback<PostItem> {
            override fun onFailure(call: Call<PostItem>, t: Throwable) {
                data.value = null
            }
            override fun onResponse(
                call: Call<PostItem>,
                response: Response<PostItem>
            ) {
                    data.value = response.body()
            }
        })
        return data
    }

    /**
     * Network call for getting a list of comments from the provided api
     */
    fun getComments(id: Int): MutableLiveData<ArrayList<CommentItem>> {
        val data = MutableLiveData<ArrayList<CommentItem>>()
        api.getComment(id).enqueue(object : Callback<ArrayList<CommentItem>> {
            override fun onFailure(call: Call<ArrayList<CommentItem>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<ArrayList<CommentItem>>,
                response: Response<ArrayList<CommentItem>>
            ) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }

    /**
     * Network call for creating a new comment and returning the created comment
     */
    fun createComment(commentItem: CommentItem, id: Int): MutableLiveData<CommentItem> {
        val data = MutableLiveData<CommentItem>()

        api.sendComment(commentItem, id).enqueue(object : Callback<CommentItem> {
            override fun onFailure(call: Call<CommentItem>, t: Throwable) {
                data.value = null
            }
            override fun onResponse(
                call: Call<CommentItem>,
                response: Response<CommentItem>
            ) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }

}