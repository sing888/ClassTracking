package com.project.group.rupp.dse.classtracking.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.databinding.ActivityProfileBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.ProfileViewModel
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.jar.Manifest

class ProfileActivity: AppCompatActivity() {
    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.roomBack.text = "<"
        binding.roomName.text = "Profile Settings"
        binding.roomBack.setOnClickListener {
            finish()
        }

        profileViewModel.getProfile(this)

        profileViewModel.profileModelUiState.observe(this, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Picasso.get().load(uiState.data?.data?.profile_url).into(binding.profileImage)
                    binding.profileUsername.setText(uiState.data?.data?.username)
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "loading failed", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        profileViewModel.updateProfileModelUiState.observe(this, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Profile updated", Snackbar.LENGTH_SHORT).show()
                    binding.profileUsernameBox.clearFocus()
                    profileViewModel.getProfile(this)
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "Profile update failed", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        profileViewModel.changeProfileModelUiState.observe(this, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Profile picture updated", Snackbar.LENGTH_SHORT).show()
                    profileViewModel.getProfile(this)
                }
                UiStateStatus.error -> {
//                    binding.temp.text = uiState.message
                    Snackbar.make(binding.root, "Profile picture update failed", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        binding.btnSave.setOnClickListener {
            profileViewModel.updateProfile(this, binding.profileUsername.text.toString())
        }

        // Set up the ActivityResultLauncher
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri: Uri? = result.data?.data
                if (selectedImageUri != null) {
                    Picasso.get().load(selectedImageUri).into(binding.profileImage)
                    val imagePart = sendImageToBackend(this, selectedImageUri)
                    binding.temp.text = imagePart.toString()
                    profileViewModel.changeProfile(this, imagePart)
                }
            }
        }

        binding.profileImage.setOnClickListener {
            openGallery()
        }

    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)
    }

    fun sendImageToBackend(context: Context, selectedImageUri: Uri): MultipartBody.Part {

        val inputStream = context.contentResolver.openInputStream(selectedImageUri)
        val imageFile = File(context.cacheDir, "temp_image") // Create a temporary file
        inputStream.use { input ->
            imageFile.outputStream().use { output ->
                input?.copyTo(output) // Copy the image data to the temporary file
            }
        }

        val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)

        return imagePart

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}