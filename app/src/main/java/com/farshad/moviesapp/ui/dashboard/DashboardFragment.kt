package com.farshad.moviesapp.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.NavGraphDirections
import com.farshad.moviesapp.R
import com.farshad.moviesapp.ViewModelAndRepository.dashboard.DashboardViewModel
import com.farshad.moviesapp.databinding.FragmentDashboardBinding
import com.farshad.moviesapp.epoxy.OnClickInterface
import com.farshad.moviesapp.model.ui.UiMovieAndGenre
import com.farshad.moviesapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class DashboardFragment: BaseFragment(R.layout.fragment_dashboard),OnClickInterface {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val dashboardViewModel: DashboardViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentDashboardBinding.bind(view)


        val controller = DashboardEpoxyController( requireContext(),this)



//        dashboardViewModel.topAndGenresLiveData.distinctUntilChanged().observe(viewLifecycleOwner){(top,genre)->
//           controller.setData(top,genre)
//        }


        //data is fetching in main activity so we don't need to fetch it here again, we can use activityViewModels
         combine(
            dashboardViewModel.firstPageMovieLiveData.asFlow() ,
            dashboardViewModel.allGenresMovieLiveData.asFlow()
        ){listOfMovie , listOfGenre ->
                UiMovieAndGenre(
                    movie = listOfMovie ,
                    genre = listOfGenre
                )

        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){
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