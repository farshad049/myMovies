package com.farshad.moviesapp.ui.movieList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
import com.farshad.moviesapp.NavGraphDirections
import com.farshad.moviesapp.ViewModelAndRepository.MovieViewModel
import com.farshad.moviesapp.ViewModelAndRepository.filter.FilterViewModel
import com.farshad.moviesapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment: Fragment() {
    private val viewModel : MovieViewModel by viewModels()
    private val filterViewModel : FilterViewModel by activityViewModels()
    private var _binding : FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val controller = MovieListEpoxyController( ::movieOnClick)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater , container , false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       val filterCarouselController = FilterCarouselEpoxyController(filterViewModel)


        binding.btnFilter.setOnClickListener {
            findNavController().navigate(MovieListFragmentDirections.actionMovieListToFilterFragment())
        }




        //set data for movie list epoxy controller
        lifecycleScope.launchWhenStarted{
            viewModel.movieListFlow.collectLatest {data->

                combine(
                    filterViewModel.filterByGenreInfoLiveData ,
                    filterViewModel.filterByImdbRateInfoLiveData
                ){genreSelectedFilters , imdbRateSelectedFilters ->

                    data.filter { toBeFilter->
                        genreSelectedFilters.selectedGenres.all { toBeFilter.genres.contains(it) } &&
                                imdbRateSelectedFilters.selectedImdbRate.all { toBeFilter.imdb_rating.toDouble() > it.toDouble() }
                    }

                }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){dataForEpoxy->
                    lifecycleScope.launch { controller.submitData(dataForEpoxy)}
                }


            }
        }

        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)






        //set data for carousel filter
        combine(
            filterViewModel.filterByGenreInfoLiveData ,
            filterViewModel.filterByImdbRateInfoLiveData
        ){genreSelectedFilters , imdbRateSelectedFilters ->
            genreSelectedFilters.selectedGenres +
                    imdbRateSelectedFilters.selectedImdbRate
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){
            filterCarouselController.setData(it)
        }

        binding.filterCarouselEpoxyRecyclerView.setController(filterCarouselController)








        binding.swipeToRefresh.setOnRefreshListener {

            viewModel.movieDataSource?.invalidate()

            lifecycleScope.launch {
                viewModel.movieListFlow.collectLatest { data->
                    controller.submitData(data)
                }
            }

            binding.epoxyRecyclerView.setControllerAndBuildModels(controller)
            binding.filterCarouselEpoxyRecyclerView.setController(filterCarouselController)

            binding.swipeToRefresh.isRefreshing = false
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


