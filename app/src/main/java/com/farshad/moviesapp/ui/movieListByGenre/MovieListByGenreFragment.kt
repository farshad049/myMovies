package com.farshad.moviesapp.ui.movieListByGenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farshad.moviesapp.NavGraphDirections
import com.farshad.moviesapp.databinding.FragmentMovieListByGenreBinding
import com.farshad.moviesapp.ui.movieListByGenre.epoxy.MovieListByGenreController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListByGenreFragment : Fragment() {

    private var _binding: FragmentMovieListByGenreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieByGenreViewModel by viewModels()
    private val safeArg: MovieListByGenreFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListByGenreBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller= MovieListByGenreController(::onMovieClick,safeArg.genreName)


        //if the value is not what has been defaulted in nav_graph, then run this code
        if (safeArg.genreId != -1){
            viewModel.submitQuery(safeArg.genreId)
        }


        lifecycleScope.launch{
            viewModel.movieByGenreFlow.collectLatest {data->
                controller.submitData(data)
            }
        }

        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)






        binding.swipeToRefresh.setOnRefreshListener{
            viewModel.pagingSource?.invalidate()
            if (safeArg.genreId != -1){
                viewModel.submitQuery(safeArg.genreId)
            }
            viewModel.pagingSource?.invalidate()
            viewModel.movieByGenreFlow
            binding.swipeToRefresh.isRefreshing = false
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