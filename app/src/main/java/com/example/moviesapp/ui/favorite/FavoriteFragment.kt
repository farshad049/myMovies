package com.example.moviesapp.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.Authentication.TokenManager
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.databinding.FragmentFavoriteBinding
import com.example.moviesapp.model.domain.DomainMovieModel
import com.example.moviesapp.roomDatabase.RoomViewModel
import com.example.moviesapp.ui.RegisterFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment:Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val roomViewModel : RoomViewModel by viewModels()
    private val controller = FavoriteFragmentEpoxyController(::onMovieClick)

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnLoginInFavorite.setOnClickListener {
            findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToRegisterFragment())
        }

        binding.epoxyRecyclerView.setController(controller)

        roomViewModel.getMovieList()

        roomViewModel.movieListLiveData.observe(viewLifecycleOwner){movieList ->
            controller.setData(movieList)
        }


        if (tokenManager.getIsLoggedIn()==true){
            binding.epoxyRecyclerView.isVisible = true
            binding.tvYouAreNotLoggeIn.isVisible = false
            binding.btnLoginInFavorite.isVisible = false
        }










    }//FUN

    private fun onMovieClick(movieId : Int){
        val directions= NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}