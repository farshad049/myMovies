package com.example.moviesapp.ui.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.Authentication.TokenManager
import com.example.moviesapp.ui.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.dashboard.DashboardViewModel
import com.example.moviesapp.databinding.FragmentDashboardBinding
import com.example.moviesapp.epoxy.OnClickInterface
import com.example.moviesapp.model.ui.UiMovieAndGenre
import com.example.moviesapp.ui.setting.SettingFragment
import com.example.moviesapp.util.BiometricAuthentication
import com.example.moviesapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.concurrent.Executor
import javax.inject.Inject

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
                if (it.movie.isNotEmpty() && it.genre.isNotEmpty()){
                    binding.swipeToRefresh.isRefreshing = false
                }
            }
            binding.epoxyRecyclerView.setController(controller)
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