package com.farshad.moviesapp.ui.submitMovie

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.farshad.moviesapp.data.model.ui.SubmitResponseModel
import com.farshad.moviesapp.data.model.ui.TextFieldStatusModel
import com.farshad.moviesapp.databinding.FragmentSubmitMultipartBinding
import com.farshad.moviesapp.ui.MainActivity
import com.farshad.moviesapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


@AndroidEntryPoint
class SubmitMovieMultipart: Fragment() {

    private var _binding: FragmentSubmitMultipartBinding? = null
    private val binding get() = _binding!!
    private val submitMultipartViewModel: SubmitMultipartViewModel by viewModels()
    private var imageRequestBody: MultipartBody.Part?= null
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubmitMultipartBinding.inflate(inflater , container , false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            submitMultipartViewModel.submitFlow.collectLatest { uploadedMovie->
                when (uploadedMovie){

                    is SubmitResponseModel.Success -> {
                        findNavController().navigate(SubmitDirections.actionSubmitToMoviesDetailFragment(uploadedMovie.data.id))

                        if (currentImageUri != null){
                            ShowNotification(activity as MainActivity, requireContext())
                                .showBigNotification(
                                    "upload status",
                                    "${uploadedMovie.data.title} Uploaded successfully !" ,
                                    "movieId" ,
                                    uploadedMovie.data.id,
                                    currentImageUri
                                )
                        } else{
                            ShowNotification(activity as MainActivity, requireContext())
                                .showNotification(
                                    "upload status",
                                    "${uploadedMovie.data.title} Uploaded successfully !" ,
                                    "movieId" ,
                                    uploadedMovie.data.id)
                        }
                    }

                    is SubmitResponseModel.Error ->{
                        Toast.makeText(requireContext(), uploadedMovie.message,Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }

            }
        }


        binding.etTitle.addTextChangedListener { binding.title.error = null }
        binding.etImdbId.addTextChangedListener { binding.imdbId.error = null }
        binding.etCountry.addTextChangedListener { binding.country.error = null }
        binding.etYear.addTextChangedListener { binding.year.error = null }



        binding.BtnSubmit.setOnClickListener {
            LoadingDialog.displayLoadingWithText(requireContext(),null,true)
            submitMultipartViewModel.validate(
                title= binding.etTitle.text.toString(),
                imdb_id = binding.etImdbId.text.toString(),
                country = binding.etCountry.text.toString(),
                year = binding.etYear.text.toString() ,
                poster = imageRequestBody
            )
        }


        //handle field errors
        submitMultipartViewModel.validationLiveData.asLiveData().observe(viewLifecycleOwner){ validationLiveData->
            LoadingDialog.hideLoading()
            when{
                validationLiveData.title is TextFieldStatusModel.Error ->{
                    binding.title.error = (validationLiveData.title).error
                    binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
                }
                validationLiveData.imdbId is TextFieldStatusModel.Error ->{
                    binding.imdbId.error = (validationLiveData.imdbId).error
                    binding.scrollView.fullScroll(ScrollView.FOCUS_UP)

                }
                validationLiveData.country is TextFieldStatusModel.Error ->{
                    binding.country.error = (validationLiveData.country).error
                    binding.scrollView.fullScroll(ScrollView.FOCUS_UP)

                }
                validationLiveData.year is TextFieldStatusModel.Error ->{
                    binding.year.error = (validationLiveData.year).error
                    binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
                }

            }
        }



        binding.ivPoster.setOnClickListener {
            GetPermission().getPermission(
                activity as MainActivity,
                requireContext() ,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                galleryLauncher,
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            )
        }






    }//FUN

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                LoadingDialog.displayLoadingWithText(requireContext(),null,true)
                lifecycleScope.launch {
                    binding.ivPoster.load(data?.data)
                    currentImageUri=data?.data
                    val imageRealPath = RealPathUtil.getRealPath(requireContext(),data?.data)
                    imageRequestBody = Convertors().convertImagePathToRequestBody(imageRealPath , "poster")
                    LoadingDialog.hideLoading()
                }
            }  else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }




//    private fun validateFields() {
//        val title = binding.etTitle.text.toString().trim()
//        val imdbId = binding.etImdbId.text.toString().trim()
//        val country = binding.etCountry.text.toString().trim()
//        val year = binding.etYear.text.toString().trim()
//
//        when {
//            title.isEmpty() -> {
//                binding.title.error = "* please enter a title"
//                binding.etTitle.addTextChangedListener { binding.title.error = null }
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter a title", Snackbar.LENGTH_LONG).show()
//                return
//            }
//            imdbId.isEmpty() -> {
//                binding.imdbId.error = "* please enter IMDB ID"
//                binding.etImdbId.addTextChangedListener { binding.imdbId.error = null }
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter IMDB ID", Snackbar.LENGTH_LONG).show()
//                return
//            }
//            country.isEmpty() -> {
//                binding.country.error = "* please enter country name"
//                binding.etCountry.addTextChangedListener { binding.etCountry.error = null }
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter country name", Snackbar.LENGTH_LONG).show()
//                return
//            }
//            year.isEmpty() -> {
//                binding.year.error = "* please enter year"
//                binding.etYear.addTextChangedListener { binding.etYear.error = null }
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter year", Snackbar.LENGTH_LONG).show()
//                return
//            }
//
//            else -> {
//
//
//                val titleBody : RequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
//                val imdbIdBody : RequestBody = imdbId.toRequestBody("text/plain".toMediaTypeOrNull())
//                val yearBody : RequestBody = year.toRequestBody("text/plain".toMediaTypeOrNull())
//                val countryBody : RequestBody = country.toRequestBody("text/plain".toMediaTypeOrNull())
//
//                LoadingDialog.displayLoadingWithText(requireContext(),null,true)
//
//                submitMultipartViewModel.pushMovieMultipart(
//                    poster =imageRequestBody,
//                    title = titleBody,
//                    imdb_id = imdbIdBody,
//                    year = yearBody,
//                    country = countryBody
//                )
//
//                submitMultipartViewModel.pushMovieMultipartLiveData.observe(viewLifecycleOwner){ uploadedMovie->
//                    if (uploadedMovie != null){
//                        LoadingDialog.hideLoading()
//                        //i don't use global action because i want to customize app:popUpTo in nav_graph
//                        findNavController().navigate(SubmitDirections.actionSubmitToMoviesDetailFragment(uploadedMovie.id))
//
//                        if (currentImageUri != null){
//                            ShowNotification(activity as MainActivity, requireContext())
//                                .showBigNotification(
//                                    "upload status",
//                                    "$title Uploaded successfully !" ,
//                                    "movieId" ,
//                                    uploadedMovie.id,
//                                    currentImageUri
//                                )
//                        } else{
//                            ShowNotification(activity as MainActivity, requireContext())
//                                .showNotification(
//                                    "upload status",
//                                    "$title Uploaded successfully !" ,
//                                    "movieId" ,
//                                    uploadedMovie.id)
//                        }
//
//
//
//                    }else{
//                        dismissProgressBar()
//                        Snackbar.make(mainActivity.findViewById(android.R.id.content),"Oops!! ,something went wrong", Snackbar.LENGTH_LONG).show()
//                    }
//
//                }
//
//
//            }
//
//        }
//    }




















    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}