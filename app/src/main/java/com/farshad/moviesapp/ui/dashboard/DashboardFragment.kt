package com.farshad.moviesapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.NavGraphDirections
import com.farshad.moviesapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment: Fragment(), OnClickInterface {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val controller = DashboardEpoxyController( requireContext(),this)


//        dashboardViewModel.topAndGenresLiveData.distinctUntilChanged().observe(viewLifecycleOwner){(top,genre)->
//           controller.setData(top,genre)
//        }



        dashboardViewModel.combinedData.asLiveData().observe(viewLifecycleOwner){
            controller.setData(it.movie , it.genre)
        }

        binding.epoxyRecyclerView.setController(controller)




        //on swipe to refresh
        binding.swipeToRefresh.setOnRefreshListener {

            dashboardViewModel.getFirstPageMovie()
            dashboardViewModel.getAllGenres()

            binding.epoxyRecyclerView.setController(controller)
            binding.swipeToRefresh.isRefreshing = false
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