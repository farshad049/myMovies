package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.arch.MovieViewModel
import com.example.moviesapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieList:BaseFragment(R.layout.fragment_movie_list) {
    private val viewModel: MovieViewModel by viewModels()
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMovieListBinding.bind(view)



        lifecycleScope.launchWhenStarted {
            viewModel.flow.collectLatest {
                Log.i("DATA1",it.toString())
            }
        }





    }//FUN





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}