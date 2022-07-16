package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMoviesListBinding
import com.example.moviesapp.model.network.NetworkMovieModel
import com.example.moviesapp.network.MovieService
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesListFragment:BaseFragment(R.layout.fragment_movies_list) {
    @Inject
    lateinit var movieService: MovieService

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMoviesListBinding.bind(view)

        lifecycleScope.launchWhenStarted {
            val response: Response<NetworkMovieModel> = movieService.getSingleMovie(1)

            Log.i("DATA1",response!!.body().toString())
        }



    }//FUN



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}