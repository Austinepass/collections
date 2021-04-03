package com.example.task10.network

import com.example.task10.data.model.CommentItem
import com.example.task10.data.model.PostItem
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Methods for making network calls wrapped around RxJava's [Observable]
 */

//interface ApiRequest {
//    @GET("posts")
//    fun getPost(): Call<ArrayList<PostItem>>
//
//    @GET("posts/{id}/comments")
//    fun getComment(
//        @Path("id") id: Int
//    ) : Call<ArrayList<CommentItem>>
//
//    @POST("posts")
//    fun sendPosts(@Body postItem: PostItem) : Call<PostItem>
//
//    @POST("posts/{id}/comments")
//    fun sendComment(@Body commentItem: CommentItem, @Path("id") id : Int) : Call<CommentItem>
//
//}

interface ApiRequest {
    @GET("posts")
    suspend fun getPost(): ArrayList<PostItem>

    @GET("posts/{id}/comments")
    suspend fun getComment(
            @Path("id") id: Int
    ) : ArrayList<CommentItem>

    @POST("posts")
    suspend fun sendPosts(@Body postItem: PostItem) : PostItem

    @POST("posts/{id}/comments")
    suspend fun sendComment(@Body commentItem: CommentItem, @Path("id") id : Int) : CommentItem

}