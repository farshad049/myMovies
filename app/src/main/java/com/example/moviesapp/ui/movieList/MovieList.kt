package com.example.moviesapp.ui.movieList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
    val controller= MovieListEpoxyController( ::movieOnClick)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMovieListBinding.bind(view)



        lifecycleScope.launchWhenStarted {
            viewModel.flow.collectLatest {
                controller.submitData(it)
            }
        }
        binding.epoxyRecyclerView.setController(controller)





    }//FUN

    private fun movieOnClick(movieId:Int) {
        val directions=MovieListDirections.actionMovieListToMoviesDetailFragment(movieId)
        findNavController().navigate(directions)

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}