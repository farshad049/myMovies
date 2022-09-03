package com.example.moviesapp.ui.submitMovie

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.MainActivity
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.databinding.FragmentSubmitMultipartBinding
import com.example.moviesapp.network.MovieService
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Constants.CHANNEL_ID
import com.example.moviesapp.util.Constants.NOTIFICATION_ID_BIG_STYLE
import com.example.moviesapp.util.RealPathUtil
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class SubmitMovieMultipart:BaseFragment(R.layout.fragment_submit_multipart) {

    @Inject lateinit var movieService: MovieService

    private var _binding: FragmentSubmitMultipartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private var imageRequestBody: MultipartBody.Part?= null
    private var currentImageUri: Uri? = null



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



                val titleBody : RequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
                val imdbIdBody : RequestBody = imdbId.toRequestBody("text/plain".toMediaTypeOrNull())
                val yearBody : RequestBody = year.toRequestBody("text/plain".toMediaTypeOrNull())
                val countryBody : RequestBody = country.toRequestBody("text/plain".toMediaTypeOrNull())

//                if (currentImageUri != null){
//                    val inputStream :InputStream= requireContext().contentResolver.openInputStream(currentImageUri!!)!!
//                    val outPutDir =context!!.cacheDir
//                    val outputFile =File.createTempFile("prefix",".jpg",outPutDir)
//                    val newFile = copyInputStreamToFile(inputStream,outputFile)
//
//                    val reqFile = newFile.asRequestBody("image/*".toMediaType())
//
//                    filePart= MultipartBody.Part.createFormData("poster", "poster.jpg", reqFile)
//                }



                showProgressBar()
                viewModel.pushMovieMultipart(
                    poster =imageRequestBody,
                    title = titleBody,
                    imdb_id = imdbIdBody,
                    year = yearBody,
                    country = countryBody
                )

                viewModel.pushMovieMultipartLiveData.observe(viewLifecycleOwner){uploadedMovie->
                    if (uploadedMovie != null){
                        dismissProgressBar()
                        //i don't use global action because i want to customize app:popUpTo in nav_graph
                        findNavController().navigate(SubmitDirections.actionSubmitToMoviesDetailFragment(uploadedMovie.id))
                        if (currentImageUri != null) showBigNotification(title) else showNotification(title)
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
                    binding.ivPoster.load(data?.data)
                    currentImageUri=data?.data
                    val imageRealPath = RealPathUtil.getRealPath(requireContext(),data?.data)
                    imageRequestBody=convertImagePathToRequestBody(imageRealPath)
                    dismissProgressBar()
                }
            }  else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }



   // convert uri to file request body
    private fun convertImagePathToRequestBody(path:String): MultipartBody.Part? {
        if (!path.equals("", ignoreCase = true)) {
            val file = File(path)
            val reqFile = file.asRequestBody("image/*".toMediaType())
            imageRequestBody = MultipartBody.Part.createFormData("poster", file.name, reqFile)
        }
        return imageRequestBody
    }






//    @Throws(IOException::class)
//    private fun copyInputStreamToFile(inputStream: InputStream,file:File):File{
//        FileOutputStream(file,false).use { outputStream->
//            var read:Int
//            val bytes =ByteArray(DEFAULT_BUFFER_SIZE)
//            while (inputStream.read(bytes).also { read = it } != -1){
//                outputStream.write(bytes,0,read)
//            }
//        }
//        return file
//    }



    private fun showNotification(title:String){
        val intent = Intent(requireContext() , MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(requireContext() , 0 , intent , 0)

        val builder = NotificationCompat.Builder(requireContext() , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_round_local_movies_24)
            .setContentTitle("Upload Status")
            .setContentText("${title}\n Uploaded successfully !")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "Upload Status"
            val channelDescription = "${title}\n Uploaded successfully !"
            val channelPriority = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID , channelName , channelPriority ).apply {
                description = channelDescription
            }

            val notificationManager = mainActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(requireContext())){
            notify(Constants.NOTIFICATION_ID, builder.build())
        }
    }





    private fun showBigNotification(title: String){
        val intent = Intent(requireContext() , MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(requireContext() , 0 , intent , 0)

        val movieImage = NotificationCompat.BigPictureStyle()
            .bigPicture(currentImageUri?.let { convertUriToBitmap(it) })
            .setBigContentTitle("Upload Status")

        val builder = NotificationCompat.Builder(requireContext() , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_round_local_movies_24)
            .setContentTitle("Upload Status")
            .setContentText("${title}\n Uploaded successfully !")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setStyle(movieImage)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "Upload Status"
            val channelDescription = "${title}\n Uploaded successfully !"
            val channelPriority = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, channelName , channelPriority ).apply {
                description = channelDescription
            }

            val notificationManager = mainActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(requireContext())){
            notify(NOTIFICATION_ID_BIG_STYLE , builder.build())
        }
    }





    private fun convertUriToBitmap(uri:Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        } else {
            val source= ImageDecoder.createSource(requireContext().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    }












    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}