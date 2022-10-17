package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.Authentication.TokenManager
import com.example.moviesapp.ViewModelAndRepository.user.UserViewModel
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentLoginBinding
import com.example.moviesapp.model.network.UserAuthModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    if (it is UserAuthModel){
                        tokenManager.saveToken(it)
                        Log.i("response",it.toString())
                        binding.etEditLoginEmail.text?.clear()
                        binding.etEditLoginPassword.text?.clear()
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserInfoFragment())
                        Toast.makeText(requireContext(), "you are logged in",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), it.toString(),Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}