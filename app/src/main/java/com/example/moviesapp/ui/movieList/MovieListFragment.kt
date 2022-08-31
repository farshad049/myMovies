package com.example.moviesapp.ui.movieList


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.example.moviesapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment:BaseFragment (R.layout.fragment_movie_list) {
    private val viewModel: MovieViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val controller= MovieListEpoxyController( ::movieOnClick)




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMovieListBinding.bind(view)

        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)



        binding.rootFilterClick.setOnClickListener {
            findNavController().navigate(MovieListFragmentDirections.actionMovieListToFilterFragment())
        }





        lifecycleScope.launchWhenStarted{
            viewModel.movieListFlow.collectLatest {data->

//                val finalData= data.filter {toBeFilter->
//                    filter.country.all { toBeFilter.country.contains(it) }
//                            && filter.genres.all { toBeFilter.genres.contains(it) }
//                }
//
//                controller.submitData(finalData)





                combine(
                    filterViewModel.store.stateFlow.map { it.movieFilterByGenre.selectedGenres },
                    filterViewModel.store.stateFlow.map { it.movieFilterByImdb.selectedImdbRate }
                ){genreSelectedFilters , imdbRateSelectedGenre ->

                    data.filter { toBeFilter->
                        genreSelectedFilters.all { toBeFilter.genres.contains(it) } &&
                                imdbRateSelectedGenre.all { toBeFilter.imdb_rating.toDouble() > it }
                    }

                }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){dataForEpoxy->
                    lifecycleScope.launch { controller.submitData(dataForEpoxy)}
                }







            }
        }

















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


