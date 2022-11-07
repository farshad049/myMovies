package com.farshad.moviesapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.R
import com.farshad.moviesapp.ViewModelAndRepository.user.UserRepository
import com.farshad.moviesapp.ViewModelAndRepository.user.UserViewModel
import com.farshad.moviesapp.databinding.FragmentLoginBinding
import com.farshad.moviesapp.model.network.LoginResponseModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment: BaseFragment(R.layout.fragment_login) {
    private val viewModel: UserViewModel by viewModels()
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

        viewModel.loginUserLiveData.distinctUntilChanged().observe(viewLifecycleOwner){response->

            when(response){
                is LoginResponseModel.Error ->{
                    Toast.makeText(requireContext(), response.error,Toast.LENGTH_SHORT).show()
                }
                is LoginResponseModel.Success ->{
                    binding.etEditLoginEmail.text?.clear()
                    binding.etEditLoginPassword.text?.clear()
                    startActivity(Intent(requireContext(),MainActivity::class.java))
                    //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserInfoFragment())
                    Toast.makeText(requireContext(), "you are logged in",Toast.LENGTH_SHORT).show()
                }
                else ->{}
            }

        }




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

            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}