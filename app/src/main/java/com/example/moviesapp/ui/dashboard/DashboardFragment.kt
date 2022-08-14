package com.example.moviesapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.dashboard.DashboardViewModel
import com.example.moviesapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment:BaseFragment(R.layout.fragment_dashboard) {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private val controller= DashboardEpoxyController( ::onMovieClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentDashboardBinding.bind(view)

        binding.epoxyRecyclerView.setController(controller)
        viewModel.getFirstPageMovie()

        viewModel.firstPageMovieLiveData.observe(viewLifecycleOwner){
            controller.setData(it)
            Log.i("dataa",it.toString())
        }









    }

    private fun onMovieClick(movieId:Int){
            val directions= NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
            findNavController().navigate(directions)
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}