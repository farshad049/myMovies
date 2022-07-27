package com.example.moviesapp.ui.movieList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.arch.MovieViewModel
import com.example.moviesapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieListFragment:BaseFragment(R.layout.fragment_movie_list) {
    private val viewModel: MovieViewModel by viewModels()
    private var _binding: FragmentMovieListBinding? = null
    private val binding by lazy { _binding!! }
    private val controller= MovieListEpoxyController( ::movieOnClick)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMovieListBinding.bind(view)



        lifecycleScope.launchWhenStarted{
            viewModel.movieListFlow.collectLatest {
                controller.submitData(it)
            }
        }
        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)





    }//FUN

    private fun movieOnClick(movieId:Int) {
        val directions=NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}