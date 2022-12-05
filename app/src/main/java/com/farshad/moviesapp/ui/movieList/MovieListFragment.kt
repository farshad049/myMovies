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
import com.farshad.moviesapp.ui.filter.FilterViewModel
import com.farshad.moviesapp.databinding.FragmentMovieListBinding
import com.farshad.moviesapp.ui.movieList.epoxy.FilterCarouselEpoxyController
import com.farshad.moviesapp.ui.movieList.epoxy.MovieListEpoxyController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment: Fragment() {
    private val movieListViewModel : MovieListViewModel by viewModels()
    private val filterViewModel : FilterViewModel by activityViewModels()
    private var _binding : FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val controller = MovieListEpoxyController( ::movieOnClick)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       val filterCarouselController = FilterCarouselEpoxyController(filterViewModel)


        binding.btnFilter.setOnClickListener {
            findNavController().navigate(MovieListFragmentDirections.actionMovieListToFilterFragment())
        }



        filterViewModel.combinedFilterDataForMovieList.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){ filters->

            lifecycleScope.launch {
                movieListViewModel.movieListFlow.collectLatest { data ->
                   val dataForEpoxy = data.filter { toBeFilter->
                        filters.genreSetOfSelectedFilters.all { toBeFilter.genres.contains(it) }
                                &&
                                filters.imdbSetOfSelectedFilters.all { toBeFilter.imdb_rating.toDouble() > it.toDouble() }
                    }
                    controller.submitData(dataForEpoxy)
                }

            }

       }



        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)







        //set data for carousel filter
        filterViewModel.combinedDataForCarouselFilterMovieList.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){ data->
            filterCarouselController.setData(data)
        }
        binding.filterCarouselEpoxyRecyclerView.setController(filterCarouselController)









        binding.swipeToRefresh.setOnRefreshListener {

            movieListViewModel.movieDataSource?.invalidate()
            movieListViewModel.movieListFlow

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


