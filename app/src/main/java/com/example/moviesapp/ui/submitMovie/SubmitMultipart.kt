package com.example.moviesapp.ui.submitMovie

import android.os.Bundle
import android.view.View
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSubmitMultipartBinding

class SubmitMultipart:BaseFragment(R.layout.fragment_submit_multipart) {
    private var _binding: FragmentSubmitMultipartBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentSubmitMultipartBinding.bind(view)



    }//FUN




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}