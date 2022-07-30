package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.arch.MovieViewModel
import com.example.moviesapp.databinding.FragmentRegisterBinding
import com.example.moviesapp.model.network.RegisterUserModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment:BaseFragment(R.layout.fragment_register) {
    private val viewModel: MovieViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding by lazy { _binding!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentRegisterBinding.bind(view)

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        binding.BtnRegister.setOnClickListener {
            validateFields()
        }



    }//FUN

    private fun validateFields() {
        val username = binding.etUserName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()


        when {
            username.isEmpty() -> {
                binding.userName.error = "* please enter a title"
                binding.etUserName.addTextChangedListener { binding.userName.error = null }
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter a user name", Snackbar.LENGTH_LONG).show()
                return
            }
            email.isEmpty() -> {
                binding.email.error = "* please enter email"
                binding.etEmail.addTextChangedListener { binding.email.error = null }
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter IMDB ID", Snackbar.LENGTH_LONG).show()
                return
            }
            password.isEmpty() -> {
                binding.password.error = "* please enter password"
                binding.etPassword.addTextChangedListener { binding.etPassword.error = null }
                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter country name", Snackbar.LENGTH_LONG).show()
                return
            }

            else -> {

                val user= RegisterUserModel(
                    email=email,
                    password = password,
                    name = username
                )

                viewModel.registerUser(user)

                viewModel.registerUserLiveData.observe(viewLifecycleOwner){registeredUser->
                    Log.i("registered",registeredUser.toString())

                }
            }
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}