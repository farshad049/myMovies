package com.example.moviesapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.Authentication.TokenManager
import com.example.moviesapp.ViewModelAndRepository.user.UserViewModel
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentUserInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserInfoFragment:BaseFragment(R.layout.fragment_user_info) {
    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentUserInfoBinding.bind(view)

        showProgressBar()
        viewModel.getUserInfo()

        viewModel.userInfoLiveData.observe(viewLifecycleOwner){userInfo->
            dismissProgressBar()
                binding.tvName.text=userInfo?.name
                binding.tvEmail.text=userInfo?.email
                binding.tvId.text= userInfo?.id.toString()
                binding.tvCreate.text=userInfo?.created_at
                binding.tvUpdate.text=userInfo?.updated_at
        }

        binding.btnLogOut.setOnClickListener {
            tokenManager.clearSharedPref()
            findNavController().navigateUp()
        }








    }//FUN



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
