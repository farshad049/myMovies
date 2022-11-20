package com.farshad.moviesapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.data.model.ui.TextFieldStatusModel
import com.farshad.moviesapp.databinding.FragmentLoginBinding
import com.farshad.moviesapp.ui.MainActivity
import com.farshad.moviesapp.util.LoadingDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment: Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenStarted {
            viewModel.loginUserLiveData.collectLatest{response->
                LoadingDialog.hideLoading()
                when(response){
                    is LoginResponseModel.Error ->{
                        Toast.makeText(requireContext(), response.error,Toast.LENGTH_SHORT).show()
                    }
                    is LoginResponseModel.Success ->{
                        binding.etEditLoginEmail.text?.clear()
                        binding.etEditLoginPassword.text?.clear()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserInfoFragment())
                        Toast.makeText(requireContext(), "you are logged in",Toast.LENGTH_SHORT).show()
                    }
                    else ->{}
                }
            }
        }




        binding.btnLogin.setOnClickListener {
            LoadingDialog.displayLoadingWithText(requireContext(),null,true)
            val userName=binding.etEditLoginEmail.text.toString()
            val password=binding.etEditLoginPassword.text.toString()
            viewModel.validate(userName , password)
        }


        //handle empty field errors
        viewModel.validationLiveData.asLiveData().observe(viewLifecycleOwner){ validationLiveData->
            LoadingDialog.hideLoading()

            when{
                validationLiveData.userName is TextFieldStatusModel.Error ->{
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),validationLiveData.userName.error!!, Snackbar.LENGTH_LONG).show()
                }
                validationLiveData.password is TextFieldStatusModel.Error ->{
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),validationLiveData.password.error!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }




    }//FUN

//    private fun validateLogin(){
//        val userName=binding.etEditLoginEmail.text.toString().trim()
//        val password=binding.etEditLoginPassword.text.toString().trim()
//
//        when{
//            userName.isEmpty() -> {
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter username", Snackbar.LENGTH_LONG).show()
//            }
//            password.isEmpty() ->{
//                Snackbar.make(mainActivity.findViewById(android.R.id.content),"please enter password", Snackbar.LENGTH_LONG).show()
//            }
//            else -> {
//
//                viewModel.loginUser(userName , password)
//            }
//        }
//    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}