package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.Authentication.TokenManager
import com.example.moviesapp.ViewModelAndRepository.UserViewModel
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentLoginBinding
import com.example.moviesapp.network.MovieService
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment:BaseFragment(R.layout.fragment_login) {
    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var movieService:MovieService
    @Inject lateinit var tokenManager: TokenManager

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
                val userNameBody :RequestBody= userName.toRequestBody()
                val passwordBody: RequestBody = password.toRequestBody()
                val grantTypeBody: RequestBody = "password".toRequestBody()



                viewModel.loginUser(userNameBody,passwordBody,grantTypeBody)

                viewModel.loginUserLiveData.observe(viewLifecycleOwner){
                    tokenManager.saveToken(it)
                    Log.i("response",it.toString())
                    binding.etEditLoginEmail.text?.clear()
                    binding.etEditLoginPassword.text?.clear()
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserInfoFragment())
                }

            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}