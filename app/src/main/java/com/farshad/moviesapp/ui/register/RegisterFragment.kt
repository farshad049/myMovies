package com.farshad.moviesapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.databinding.FragmentRegisterBinding
import com.farshad.moviesapp.data.model.network.RegisterResponseModel
import com.farshad.moviesapp.data.model.ui.TextFieldStatusModel
import com.farshad.moviesapp.ui.MainActivity
import com.farshad.moviesapp.util.LoadingDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private val viewModel: RegisterViewModel by viewModels()
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



        if (tokenManager.isLoggedIn()){
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToUserInfoFragment())
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }




        lifecycleScope.launchWhenStarted {

            viewModel.registerUserLiveData.collectLatest{registeredUser->
                LoadingDialog.hideLoading()
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

        }



        binding.BtnRegister.setOnClickListener {
            LoadingDialog.displayLoadingWithText(requireContext(),null,true)
            val username = binding.etUserName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.validate(userName = username , email = email , password = password)
        }

        //handle empty field errors
        viewModel.validationLiveData.asLiveData().observe(viewLifecycleOwner){ validationLiveData->
            LoadingDialog.hideLoading()

            when{
                validationLiveData.userName is TextFieldStatusModel.Error ->{
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),validationLiveData.userName.error!!, Snackbar.LENGTH_LONG).show()
                }
                validationLiveData.email is TextFieldStatusModel.Error ->{
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),validationLiveData.email.error!!, Snackbar.LENGTH_LONG).show()
                }
                validationLiveData.password is TextFieldStatusModel.Error ->{
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),validationLiveData.password.error!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }



    }//FUN

//    private fun validateFields() {
//        val username = binding.etUserName.text.toString().trim()
//        val email = binding.etEmail.text.toString().trim()
//        val password = binding.etPassword.text.toString().trim()
//
//
//        when {
//            username.isEmpty() -> {
//                binding.userName.error = "* please enter a title"
//                binding.etUserName.addTextChangedListener { binding.userName.error = null }
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter a user name", Snackbar.LENGTH_LONG).show()
//                return
//            }
//            email.isEmpty() -> {
//                binding.email.error = "* please enter email"
//                binding.etEmail.addTextChangedListener { binding.email.error = null }
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter IMDB ID", Snackbar.LENGTH_LONG).show()
//                return
//            }
//            password.isEmpty() -> {
//                binding.password.error = "* please enter password"
//                binding.etPassword.addTextChangedListener { binding.etPassword.error = null }
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter country name", Snackbar.LENGTH_LONG).show()
//                return
//            }
//
//            else -> {
//
//                val user= RegisterUserModel(
//                    email=email,
//                    password = password,
//                    name = username
//                )
//
//                viewModel.registerUser(user)
//
//
//            }
//        }
//    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}