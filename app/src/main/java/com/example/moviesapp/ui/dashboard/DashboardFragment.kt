package com.example.moviesapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.ViewModelAndRepository.dashboard.DashboardViewModel
import com.example.moviesapp.databinding.FragmentDashboardBinding
import com.example.moviesapp.epoxy.OnClickInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment:BaseFragment(R.layout.fragment_dashboard),OnClickInterface {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val controller= DashboardEpoxyController( this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentDashboardBinding.bind(view)

        binding.epoxyRecyclerView.setController(controller)


        dashboardViewModel.getFirstPageMovie()
        dashboardViewModel.getAllGenres()


        binding.rootSearchClick.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSearchFragment())
        }



        dashboardViewModel.topAndGenresLiveData.distinctUntilChanged().observe(viewLifecycleOwner){(top,genre)->
           controller.setData(top,genre)
        }










    }//FUN


    override fun onGenreClick(genreId: Int,genreName:String) {
        val directions= DashboardFragmentDirections.actionDashboardFragmentToMovieListByGenre(genreId,genreName)
        findNavController().navigate(directions)

    }

    override fun onMovieClick(movieId:Int){
        val directions= NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)
    }









    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}