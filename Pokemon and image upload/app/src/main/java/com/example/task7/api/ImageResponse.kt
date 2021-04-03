package com.example.task7.api

data class ImageResponse (
        val payload: Payload,
        val status: Long,
        val message: String
)



data class Payload(
        val fileId: String,
        val fileType: String,
        val fileName: String,
        val downloadUri: String, val uploadStatus: Boolean
)




