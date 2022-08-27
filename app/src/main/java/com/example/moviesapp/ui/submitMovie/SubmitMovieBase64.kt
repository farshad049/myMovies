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
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.databinding.FragmentSubmitBase64Binding
import com.example.moviesapp.model.network.UploadMovieModelStringPoster
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class SubmitMovieBase64:BaseFragment(R.layout.fragment_submit_base64) {

    private var _binding: FragmentSubmitBase64Binding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var path:String=""
    private val viewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentSubmitBase64Binding.bind(view)


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

                val movieForUpload=UploadMovieModelStringPoster(
                    title = title,
                    imdb_id = imdbId,
                    country = country,
                    year = year.toInt(),
                    poster = "data:image/jpeg;base64,$path"
                )

                showProgressBar()
                viewModel.pushMovieBase64(movieForUpload)

                viewModel.pushMovieBase64LiveData.observe(viewLifecycleOwner){ uploadedMovie->
                    if (uploadedMovie != null){
                        dismissProgressBar()
                        findNavController().navigate(SubmitDirections.actionSubmitToMoviesDetailFragment(uploadedMovie.id))
                    }else{
                        dismissProgressBar()
                        Snackbar.make(mainActivity.findViewById(android.R.id.content),"Oops!! ,something went wrong", Snackbar.LENGTH_LONG).show()
                    }
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
                        currentImageUri = data?.data
                        binding.ivPoster.load(data?.data)
                        val bitmap = convertUriToBitmap(data?.data!!)
                        path= convertBitmapTOBase64(bitmap)
                        dismissProgressBar()
                    }
            }  else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
          }
        }


    private fun convertUriToBitmap(uri:Uri):Bitmap{
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        } else {
            val source=ImageDecoder.createSource(requireContext().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    }

    private fun convertBitmapTOBase64(bitmap:Bitmap):String{
        val stream= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
        val image= stream.toByteArray()
        return Base64.encodeToString(image,Base64.DEFAULT)
    }














    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}