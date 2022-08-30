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
import com.example.moviesapp.util.Constants.appliedFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
    private val filter= appliedFilter
    var filterSet:Set<String> = emptySet()





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



                filterViewModel.store.stateFlow.map { it.productFilterInfo.selectedGenres }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){selectedFilters->
                    val finalData=data.filter { toBeFilter->
                        selectedFilters.all { toBeFilter.genres.contains(it) }
                    }



//                        controller.addLoadStateListener {loadState->
//                            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached){
//                                Toast.makeText(requireContext(),"nothing found",Toast.LENGTH_LONG).show()
//                                binding.tvNothingFound.isVisible = true
//                                binding.epoxyRecyclerView.isVisible = false
//                            }else{
                               lifecycleScope.launch { controller.submitData(finalData)}
//                                binding.tvNothingFound.isVisible = false
//                                binding.epoxyRecyclerView.isVisible = true
//                            }
//
//                        }







                }


            }
        }

















    }//FUN

    private fun movieOnClick(movieId:Int) {
        val directions=NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)

    }




//    var choice = arrayOf("kotlin","java")
//    var selected= booleanArrayOf(false,false)
//    fun dialog2(){
//
//        var alertDialog=AlertDialog.Builder(requireContext())
//        alertDialog.setTitle("Filter Option")
//        alertDialog.setMultiChoiceItems(choice,selected){ dialogInterface:DialogInterface , postion:Int, check:Boolean ->
//            if (check){
//
//            }else{
//
//            }
//        }
//        alertDialog.setPositiveButton("ok",null)
//        alertDialog.setCancelable(false)
//        alertDialog.show()
//    }








    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


