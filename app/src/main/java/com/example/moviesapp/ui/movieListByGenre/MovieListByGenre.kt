package com.example.moviesapp.ui.movieListByGenre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.ui.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.movieListByGenre.MovieByGenreViewModel
import com.example.moviesapp.databinding.FragmentMovieListByGenreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieListByGenre: BaseFragment(R.layout.fragment_movie_list_by_genre) {

    private var _binding: FragmentMovieListByGenreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieByGenreViewModel by viewModels()
    private val safeArg: MovieListByGenreArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMovieListByGenreBinding.bind(view)

        val controller=MovieListByGenreController(::onMovieClick,safeArg.genreName)


        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)

        //if the value is not what has been defaulted in nav_graph, then run this code
        if (safeArg.genreId != -1){
            viewModel.submitQuery(safeArg.genreId)
        }



        lifecycleScope.launchWhenStarted{
            viewModel.movieByGenreFlow.collectLatest {data->
                controller.submitData(data)
            }
        }







    }//FUN

    private fun onMovieClick(movieId: Int) {
        val directions= NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}