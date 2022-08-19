package com.example.moviesapp.ui.movieList


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.databinding.FragmentMovieListBinding
import com.example.moviesapp.util.Constants.appliedFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieListFragment:BaseFragment (R.layout.fragment_movie_list) {
    private val viewModel: MovieViewModel by viewModels()
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val controller= MovieListEpoxyController( ::movieOnClick)
    private val filter= appliedFilter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMovieListBinding.bind(view)

        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)



        binding.rootFilterClick.setOnClickListener {
            findNavController().navigate(MovieListFragmentDirections.actionMovieListToFilterFragment())
        }





        lifecycleScope.launchWhenStarted{
            viewModel.movieListFlow.collectLatest {data->

                val finalData= data.filter {toBeFilter->
                    filter.country.all { toBeFilter.country.contains(it) }
                            && filter.genres.all { toBeFilter.genres.contains(it) }
                }

                controller.submitData(finalData)
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


