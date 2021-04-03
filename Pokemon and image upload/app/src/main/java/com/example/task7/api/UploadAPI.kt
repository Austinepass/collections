package com.example.task7.api


import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

const val  IMAGE_BASE_URL = "https://darot-image-upload-service.herokuapp.com/"
interface UploadAPI {

    @Multipart
    @POST("api/v1/upload")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("multipart/form-data")body : RequestBody
    ) : Call<ImageResponse>

    companion object{
        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()



        operator fun invoke() : UploadAPI{
            return Retrofit.Builder()
                .baseUrl(IMAGE_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UploadAPI::class.java)
        }
    }

}