package com.farshad.moviesapp.ui.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.databinding.FragmentUserInfoBinding
import com.farshad.moviesapp.util.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserInfoFragment: Fragment() {
    private val viewModel: UserInfoViewModel by viewModels()
    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInfoBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoadingDialog.displayLoadingWithText(requireContext(),null,true)

        viewModel.userInfoLiveData.observe(viewLifecycleOwner){userInfo->
            LoadingDialog.hideLoading()
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
