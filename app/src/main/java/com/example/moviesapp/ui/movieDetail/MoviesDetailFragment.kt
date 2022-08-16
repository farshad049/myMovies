package com.example.moviesapp.ui.movieDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.moviesapp.BaseFragment
import com.example.moviesapp.NavGraphDirections
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.MovieViewModel
import com.example.moviesapp.databinding.FragmentMoviesDetailBinding
import com.example.moviesapp.network.ApiClient
import com.example.moviesapp.network.MovieService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesDetailFragment:BaseFragment(R.layout.fragment_movies_detail) {
    @Inject lateinit var movieService: MovieService
    @Inject lateinit var apiClient: ApiClient
    private val viewModel:MovieViewModel by viewModels()
    private val safeArg:MoviesDetailFragmentArgs by navArgs()


    private var _binding: FragmentMoviesDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMoviesDetailBinding.bind(view)

        val controller= MovieDetailEpoxyController(::onSimilarMovieClick)


        showProgressBar()
        viewModel.getMovieById(safeArg.movieId)
        viewModel.movieByIdLiveData.observe(viewLifecycleOwner){movieById->
            dismissProgressBar()
            binding.ivMovie.load(movieById?.poster)
            binding.tvMovieName.text=movieById?.title
            binding.tvIMDB.text= movieById?.imdb_rating
            binding.tvYear.text= movieById?.year
            binding.tvRate.text= movieById?.rated
            binding.tvCountry.text= movieById?.country
            binding.tvDirector.text= movieById?.director
            binding.tvGenres.text= movieById?.genres.toString()
            binding.tvActors.text= movieById?.actors
            binding.tvPlot.text= movieById?.plot



            val genreId =genreNameToId(movieById?.genres?.component1())

            viewModel.getMovieByGenre(genreId)
            viewModel.movieByGenreLiveData.observe(viewLifecycleOwner){movieByGenre->


                controller.setData(movieById,movieByGenre)
                binding.imageEpoxyRecyclerView.setController(controller)
            }
        }




















    }//FUN

    private fun onSimilarMovieClick(movieId:Int ) {
        val directions= NavGraphDirections.actionGlobalToMovieDetailFragment(movieId)
        findNavController().navigate(directions)

    }


    private fun genreNameToId(genreName:String?):Int{
        return when(genreName){
         "Crime" -> 1
         "Drama"-> 2
            "Action" -> 3
            "Biography" -> 4
            "History" -> 5
            "Adventure" -> 6
            "Fantasy" -> 7
            "Western" -> 8
            "Comedy" -> 9
            "Sci-Fi" -> 10
            "Mystery" -> 11
            "Thriller" -> 12
            "War" -> 13
            "Animation" -> 14
            "Romance" -> 15
            "Horror" -> 16
            "Music" -> 17
            "Film-Noir" -> 18
            "Musical" -> 19
            "Sport" ->20
            else -> 0
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}