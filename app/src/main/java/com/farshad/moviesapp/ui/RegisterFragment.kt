package com.farshad.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.R
import com.farshad.moviesapp.ViewModelAndRepository.user.UserViewModel
import com.farshad.moviesapp.databinding.FragmentLoginBinding
import com.farshad.moviesapp.databinding.FragmentRegisterBinding
import com.farshad.moviesapp.model.network.RegisterResponseModel
import com.farshad.moviesapp.model.network.RegisterUserModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment: BaseFragment(R.layout.fragment_register) {
    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (tokenManager.getIsLoggedIn()==true){
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToUserInfoFragment())
        }


        viewModel.registerUserLiveData.distinctUntilChanged().observe(viewLifecycleOwner){registeredUser->
            when(registeredUser){
                is RegisterResponseModel.Success -> {
                    binding.etUserName.text?.clear()
                    binding.etPassword.text?.clear()
                    binding.etEmail.text?.clear()
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                }
                is RegisterResponseModel.Error -> {
                    Toast.makeText(requireContext(), registeredUser.error,Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }

        }



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


            }
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}