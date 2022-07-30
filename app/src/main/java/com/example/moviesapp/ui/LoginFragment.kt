package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.arch.MovieViewModel
import com.example.moviesapp.databinding.FragmentLoginBinding
import com.example.moviesapp.network.MovieService
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment:BaseFragment(R.layout.fragment_login) {
    private val viewModel: MovieViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding by lazy { _binding!! }

    @Inject lateinit var movieService:MovieService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            validateLogin()
        }



    }//FUN

    private fun validateLogin(){
        val userName=binding.etEditLoginEmail.text.toString().trim()
        val password=binding.etEditLoginPassword.text.toString().trim()

        when{
            userName.isEmpty() -> {
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter username", Snackbar.LENGTH_LONG).show()
            }
            password.isEmpty() ->{
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter password", Snackbar.LENGTH_LONG).show()
            }
            else -> {


            val pass="password"
                lifecycleScope.launch {
                   val request= movieService.loginUser(
                        username = userName.toRequestBody(),
                        password = password.toRequestBody(),
                        grantType = pass.toRequestBody()
                    )
                    Log.i("login",request.body().toString())
                }











            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}