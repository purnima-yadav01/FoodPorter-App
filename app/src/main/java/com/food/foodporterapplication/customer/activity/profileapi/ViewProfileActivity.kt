package com.food.foodporterapplication.customer.activity.profileapi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.activity.profileapi.model.GetProfileModelView
import com.food.foodporterapplication.customer.activity.profileapi.updateprofileapi.UpdateProfileModelView
import com.food.foodporterapplication.customer.classes.CustomProgressDialog
import com.food.foodporterapplication.databinding.ActivityViewProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@AndroidEntryPoint
class ViewProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewProfileBinding
    private var editImgFlag = "1"
    private val profileModelView: GetProfileModelView by viewModels()
    private val updateProfile: UpdateProfileModelView by viewModels()
    private val CAMERA_PERMISSION_CODE = 101
    private var countryCodeNew = ""
    val context = this@ViewProfileActivity
    private val progressDialog by lazy { CustomProgressDialog(activity) }
    private lateinit var activity: Activity
    private var selectedImageFile: File? = null
    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE
            )

        } else {
            // Permission already granted, proceed with camera operation
            selectImage()
        }
    }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap

                if (imageBitmap != null) {
                    // Convert Bitmap to File
                    selectedImageFile = bitmapToFile(imageBitmap)
                    setImageOnImageView(selectedImageFile)
                    // Now you can use the 'imageFile' as needed
                    binding.profilePic.setImageBitmap(imageBitmap)
                    Log.e(
                        "moveIdenetity",
                        "CustomerProfile...Camera" + "selectedImageFile.." + selectedImageFile
                    )
                } else {
                    Toast.makeText(
                        this@ViewProfileActivity,
                        "Failed to capture image",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                Toast.makeText(
                    this@ViewProfileActivity,
                    "Image capture cancelled",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        setupCountryCodeSpinner()
        profileApi()
        profileObserver()
        updateProfileObserver()

        editImgFlag = intent.getStringExtra("editImg").toString()

        if (editImgFlag == "1") {
            binding.editProfileImg.visibility = View.VISIBLE
        } else {
            binding.editProfileImg.visibility = View.GONE
        }

        binding.imaBackMain.setOnClickListener {
            finish()
        }

        binding.updateProfileBtn.setOnClickListener {
            updateProfileApi()
        }

        binding.imgPicClick.setOnClickListener {
            requestCameraPermission()
        }
    }

    private fun setupCountryCodeSpinner() {
        binding.countryCodeTxt.setCountryForPhoneCode(+973)
        binding.countryCodeTxt.setOnCountryChangeListener {
            // Extract the country code without the '+' sign
            countryCodeNew = binding.countryCodeTxt.selectedCountryCodeWithPlus.toString()
            Log.e("LogA", "countryCode-$countryCodeNew")
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Select Image")
        builder.setItems(options) { dialog, item ->

            when {
                options[item] == "Take Photo" -> openCamera()
                options[item] == "Choose from Gallery" -> openGallery()
                options[item] == "Cancel" -> dialog.dismiss()
            }
        }

        builder.show()
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(this@ViewProfileActivity.packageManager) != null) {
            takePictureLauncher.launch(cameraIntent)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        selectImageLauncher.launch(galleryIntent)
    }

    private fun setImageOnImageView(imageFile: File?) {
        if (imageFile != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(
                this.contentResolver, Uri.fromFile(imageFile)
            )
            binding.profilePic.setImageBitmap(bitmap)
        }
    }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val selectedImageUri = data.data
                    if (selectedImageUri != null) {
                        selectedImageFile = convertUriToFile(selectedImageUri)
                        if (selectedImageFile != null) {
                            // Now you have the image file in File format, you can use it as needed.
                            setImageOnImageView(selectedImageFile)
                            Log.e(
                                "moveIdenetity",
                                "CustomerProfile...Gallery" + "selectedImageFile.." + selectedImageFile
                            )
                        } else {
                            // Handle the case where conversion to File failed
                            showToast("Error converting URI to File")
                        }
                    } else {
                        // Handle the case where the URI is null
                        showToast("Selected image URI is null")
                    }
                } else {
                    // Handle the case where data is null
                    showToast("No data received")
                }
            } else {
                // Handle the case where the result code is not RESULT_OK
                showToast("Action canceled")
            }
        }


    private fun convertUriToFile(uri: Uri): File? {
        try {
            val inputStream: InputStream? =
                this.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val fileName = getFileNameFromUri(uri)
                val outputFile = File(this.cacheDir, fileName)
                val outputStream = FileOutputStream(outputFile)
                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                return outputFile
            }

        } catch (e: Exception) {
            // Log the exception for debugging purposes
            Log.e("ConversionError", "Error converting URI to File: ${e.message}", e)
        }

        return null

    }

    private fun showToast(message: String) {
        Toast.makeText(this@ViewProfileActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun getFileNameFromUri(uri: Uri): String {
        var result: String? = null
        val contentResolver: ContentResolver = this.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    result = it.getString(displayNameIndex)
                }
            }
        }

        return result ?: "file"
    }

    private fun bitmapToFile(bitmap: Bitmap): File {
        // Create a file in the cache directory
        val file = File(activity.cacheDir, "image.jpg")

        // Convert the bitmap to a byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bitmapData = byteArrayOutputStream.toByteArray()

        // Write the bytes into the file
        FileOutputStream(file).use { fileOutputStream ->
            fileOutputStream.write(bitmapData)
            fileOutputStream.flush()
        }

        return file
    }

    //get Profile api
    private fun profileApi() {

        profileModelView.profileUser(this)

    }

    private fun profileObserver() {
        profileModelView.progressIndicator.observe(this) {
            // Handle loading indicator if needed
        }

        profileModelView.mGetProfileResponse.observe(this) {
            val response = it.peekContent()
            val message = response.message
            val success = response.success
            val data = response.data

            if (success == true && data != null) {
                binding.userFirstName.setText(data.firstName ?: "")
                binding.userLastName.setText(data.lastName ?: "")
                binding.userEmailEt.setText(data.email ?: "")

                val phoneCode = data.countryCode?.replace("+", "") ?: "91"
                binding.countryCodeTxt.setCountryForPhoneCode(phoneCode.toIntOrNull() ?: 91)

                binding.userPhoneNumber.setText(data.phone ?: "")
                binding.userAddressEt.setText(data.address ?: "")
            } else {
                Toast.makeText(this, message ?: "Profile not found", Toast.LENGTH_LONG).show()
            }
        }

        profileModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    // update prpfile
    private fun updateProfileApi() {
        val firstName = binding.userFirstName.text.toString().toRequestBody(MultipartBody.FORM)
        val lastName = binding.userLastName.text.toString().toRequestBody(MultipartBody.FORM)
        val email = binding.userEmailEt.text.toString().toRequestBody(MultipartBody.FORM)
        val phone = binding.userPhoneNumber.text.toString().toRequestBody(MultipartBody.FORM)
        val countryCode =
            binding.countryCodeTxt.selectedCountryCodeWithPlus.toString().toRequestBody(
                MultipartBody.FORM
            )

        val address = binding.userAddressEt.text.toString().toRequestBody(MultipartBody.FORM)
        val profile_image: MultipartBody.Part = if (selectedImageFile == null) {

            MultipartBody.Part.createFormData(
                "profile_image", selectedImageFile?.name, "".toRequestBody("*image/*".toMediaTypeOrNull())
            )

        } else {
            MultipartBody.Part.createFormData(
                "profile_image",
                selectedImageFile?.name,
                selectedImageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
            )
        }

        updateProfile.updateProfileUser(
            this,
            firstName,
            lastName,
            email,
            phone,
            countryCode,
            address,
            profile_image
        )
    }

    @SuppressLint("SuspiciousIndentation")
    private fun updateProfileObserver() {
        updateProfile.progressIndicator.observe(this) {}
        updateProfile.mUpdateProfileResponse.observe(this) {
            val msg = it.peekContent().message
            profileApi()
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

            updateProfile.errorResponse.observe(this) {
                ErrorUtil.handlerGeneralError(this, it)
            }
        }
    }
}