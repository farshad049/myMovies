package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import coil.load
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMoviesDetailBinding
import com.example.moviesapp.model.network.NetworkMovieModel
import com.example.moviesapp.network.MovieService
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesDetailFragment:BaseFragment(R.layout.fragment_movies_detail) {
    @Inject
    lateinit var movieService: MovieService

    private var _binding: FragmentMoviesDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMoviesDetailBinding.bind(view)

        val controller=MovieImageController()


        //in order to scroll images completely
        LinearSnapHelper().attachToRecyclerView(binding.epoxyRecyclerView)



        lifecycleScope.launchWhenStarted {
            val response: Response<NetworkMovieModel> = movieService.getSingleMovie(1)

            binding.ivMovie.load(response.body()?.poster)
            binding.tvMovieName.text=response.body()?.title
            binding.tvIMDB.text=response.body()?.imdb_rating
            binding.tvYear.text=response.body()?.year
            binding.tvRate.text=response.body()?.rated
            binding.tvCountry.text=response.body()?.country
            binding.tvDirector.text=response.body()?.director
            binding.tvGenres.text=response.body()?.genres.toString()
            binding.tvActors.text=response.body()?.actors
            binding.tvPlot.text=response.body()?.plot
            controller.setData(response.body())
            binding.epoxyRecyclerView.setController(controller)






            Log.i("DATA1",response!!.body().toString())
        }



    }//FUN



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}