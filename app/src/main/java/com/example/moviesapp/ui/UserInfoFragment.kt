package com.example.moviesapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.moviesapp.ViewModelAndRepository.UserViewModel
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentUserInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment:BaseFragment(R.layout.fragment_user_info) {
    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentUserInfoBinding? = null
    private val binding by lazy { _binding!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentUserInfoBinding.bind(view)

        viewModel.getUserInfo()

        viewModel.userInfoLiveData.observe(viewLifecycleOwner){userInfo->
            if (userInfo != null){
                binding.tvName.text=userInfo.name
                binding.tvEmail.text=userInfo.email
                binding.tvId.text= userInfo.id.toString()
                binding.tvCreate.text=userInfo.created_at
                binding.tvUpdate.text=userInfo.updated_at
            }else{
                binding.tvAccess.text="you don't have access"
            }
        }








    }//FUN



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
