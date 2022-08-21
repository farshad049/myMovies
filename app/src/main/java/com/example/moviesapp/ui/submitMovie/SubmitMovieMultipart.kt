package com.example.moviesapp.ui.submitMovie

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.databinding.FragmentSubmitMultipartBinding
import com.example.moviesapp.network.MovieService
import com.example.moviesapp.util.RealPathUtil
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class SubmitMovieMultipart:BaseFragment(R.layout.fragment_submit_multipart) {

    @Inject lateinit var movieService: MovieService

    private var _binding: FragmentSubmitMultipartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private var imageRequestBody: MultipartBody.Part? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentSubmitMultipartBinding.bind(view)

        binding.ivPoster.setOnClickListener {
            choosePhotoFromGallery()
        }

        binding.BtnSubmit.setOnClickListener {
            validateFields()
        }




    }//FUN

    private fun validateFields() {
        val title = binding.etTitle.text.toString().trim()
        val imdbId = binding.etImdbId.text.toString().trim()
        val country = binding.etCountry.text.toString().trim()
        val year = binding.etYear.text.toString().trim()

        when {
            title.isEmpty() -> {
                binding.title.error = "* please enter a title"
                binding.etTitle.addTextChangedListener { binding.title.error = null }
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter a title", Snackbar.LENGTH_LONG).show()
                return
            }
            imdbId.isEmpty() -> {
                binding.imdbId.error = "* please enter IMDB ID"
                binding.etImdbId.addTextChangedListener { binding.imdbId.error = null }
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter IMDB ID", Snackbar.LENGTH_LONG).show()
                return
            }
            country.isEmpty() -> {
                binding.country.error = "* please enter country name"
                binding.etCountry.addTextChangedListener { binding.etCountry.error = null }
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter country name", Snackbar.LENGTH_LONG).show()
                return
            }
            year.isEmpty() -> {
                binding.year.error = "* please enter year"
                binding.etYear.addTextChangedListener { binding.etYear.error = null }
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter year", Snackbar.LENGTH_LONG).show()
                return
            }

            else -> {



                val titleBody : RequestBody = title.toRequestBody()
                val imdbIdBody : RequestBody = imdbId.toRequestBody()
                val yearBody : RequestBody = year.toRequestBody()
                val countryBody : RequestBody = country.toRequestBody()



                lifecycleScope.launch{
                    val a=   movieService.pushMoviesMulti(
                           poster = imageRequestBody,
                           title = titleBody,
                           imdb_id = imdbIdBody,
                           year = yearBody,
                           country = countryBody
                       )
                    Log.i("test",a.body().toString())
                }




            }

        }
    }




    private fun choosePhotoFromGallery() {
        PermissionX.init(activity)
            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    galleryLauncher.launch(intent)

                } else {
                    Toast.makeText(
                        requireContext(),
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                showProgressBar()
                lifecycleScope.launch {
                    binding.ivPoster.load(data?.data)
                    val imageRealPath =RealPathUtil.getRealPath(requireContext(),data?.data)
                    imageRequestBody=convertImagePathToRequestBody(imageRealPath)
                    dismissProgressBar()
                }
            }  else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }





    private fun convertImagePathToRequestBody(path:String):MultipartBody.Part?{
        if (!path.equals("", ignoreCase = true)) {
            val file = File(path)
            val reqFile = file.asRequestBody("*/*".toMediaTypeOrNull())
            imageRequestBody = MultipartBody.Part.createFormData("movie_poster", file.name, reqFile)
        }
        return imageRequestBody
    }










    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}