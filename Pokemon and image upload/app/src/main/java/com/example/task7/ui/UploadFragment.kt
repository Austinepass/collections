package com.example.task7.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.task7.R
import com.example.task7.api.ImageResponse
import com.example.task7.api.UploadAPI
import com.example.task7.api.UploadResponseBody
import com.example.task7.databinding.FragmentUploadBinding
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import retrofit2.Callback
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class UploadFragment : Fragment(), UploadResponseBody.UploadCallback {
    var uploadBinding: FragmentUploadBinding? = null
    lateinit var binding: FragmentUploadBinding
    private var selectedImage: Uri? = null

    companion object {

        //Button click
        private const val IMAGE_PICK_CODE = 1000

        //Permission code
        private const val PERMISSION_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_upload, container, false)
        binding = FragmentUploadBinding.bind(view)
        uploadBinding = binding

        binding.uploadImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE)
                } else {
                    pickImage()
                }
            }  else {
                pickImage()
            }
        }

        /**
         * Set up upload button to upload selected image from server
         */
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }

        return binding.root
    }

    /**
     * Use implicit intent to fetch an image
     */
    private fun pickImage() {

        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("images/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes )
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }

    /**
     * UploadImage function for making network call and uploading selected image to server
     */
    private fun uploadImage() {
        if (selectedImage == null) {
            Snackbar.make(requireView(), "Select an image", Snackbar.LENGTH_LONG).show()
            return
        }

        val parcelFileDescriptor =
            requireActivity().contentResolver?.openFileDescriptor(selectedImage!!, "r", null) ?: return
        val file = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(selectedImage!!))
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar.progress = 0
        val body = UploadResponseBody(file, "image", this)
        UploadAPI().uploadImage(
            MultipartBody.Part.createFormData("file", file.name, body),
        RequestBody.create(MediaType.parse("multipart/form-data"),"Image uploaded")
            ).enqueue(object : Callback<ImageResponse> {
                override fun onFailure(call: retrofit2.Call<ImageResponse>, t: Throwable) {
                    Snackbar.make(requireView()," Failure ${t.message} ${t.localizedMessage}",Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: retrofit2.Call<ImageResponse>,
                    response: retrofit2.Response<ImageResponse>
                ) {
                    Snackbar.make(requireView(),"Image Uploaded Successfully",Snackbar.LENGTH_LONG).show()
                    Log.i("Response", response.body().toString())
                    binding.uploadButton.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
    })
    }

    /**
     * Get the image that was selected and save it
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImage = data?.data
            binding.uploadImage.setImageURI(selectedImage)
        }
    }


    /**
     * Handles the result of the permission request
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImage()
            }else{
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    //ContentResolver extension function to query and return the name of our image
    private fun ContentResolver.getFileName(uri: Uri) : String{
        var name = ""
        val cursor = query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name

    }

    //Set the the progress of the image upload to the progress bar
    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }

    //Release the ViewBinding instance
    override fun onDestroyView() {
        super.onDestroyView()
        uploadBinding = null
    }

}