package com.farshad.moviesapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.farshad.moviesapp.Authentication.TokenManager
import com.farshad.moviesapp.NavGraphDirections
import com.farshad.moviesapp.databinding.FragmentFavoriteBinding
import com.farshad.moviesapp.ui.favorite.epoxy.EmptyFavoriteMovieListEpoxyModel
import com.farshad.moviesapp.ui.favorite.epoxy.FavoriteMovieEpoxyModel
import com.farshad.moviesapp.epoxy.HeaderEpoxyModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment:Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val roomViewModel : FavoriteFragmentViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnLoginInFavorite.setOnClickListener {
            findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToRegisterFragment())
        }


        if (tokenManager.isLoggedIn()){
            binding.epoxyRecyclerView.isVisible = true
            binding.tvYouAreNotLoggeIn.isVisible = false
            binding.btnLoginInFavorite.isVisible = false
        }




        roomViewModel.favoriteMovieListMutableLiveData.asLiveData().distinctUntilChanged().observe(viewLifecycleOwner){data ->
            binding.epoxyRecyclerView.withModels {
                if (data.isNullOrEmpty()){
                    EmptyFavoriteMovieListEpoxyModel().id("empty").addTo(this)
                    return@withModels
                }

                HeaderEpoxyModel("Favorite Movies").id("favorite_movies").addTo(this)

                data.forEach {
                    FavoriteMovieEpoxyModel(it , ::onMovieClick).id(it.id).addTo(this)
                }
            }
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