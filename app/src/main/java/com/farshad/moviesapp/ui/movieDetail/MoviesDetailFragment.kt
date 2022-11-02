package com.farshad.moviesapp.ui.movieDetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.farshad.moviesapp.ui.BaseFragment
import com.farshad.moviesapp.NavGraphDirections
import com.farshad.moviesapp.R
import com.farshad.moviesapp.ViewModelAndRepository.MovieViewModel
import com.farshad.moviesapp.databinding.FragmentMoviesDetailBinding
import com.farshad.moviesapp.model.ui.UiMovieDetailModel
import com.farshad.moviesapp.network.ApiClient
import com.farshad.moviesapp.network.MovieService
import com.farshad.moviesapp.roomDatabase.Entity.FavoriteMovieEntity
import com.farshad.moviesapp.roomDatabase.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@AndroidEntryPoint
class MoviesDetailFragment: BaseFragment(R.layout.fragment_movies_detail) {
    @Inject lateinit var movieService: MovieService
    @Inject lateinit var apiClient: ApiClient
    private val viewModel:MovieViewModel by viewModels()
    private val roomViewModel : RoomViewModel by viewModels()
    private val safeArg:MoviesDetailFragmentArgs by navArgs()

    private var _binding: FragmentMoviesDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentMoviesDetailBinding.bind(view)

        val controller= MovieDetailEpoxyController(::onSimilarMovieClick)


        showProgressBar()

        viewModel.getMovieById(safeArg.movieId)
        roomViewModel.getFavoriteMovieList()


        combine(
            viewModel.movieByIdLiveData.asFlow() ,
            roomViewModel.favoriteMovieListMutableLiveData.asFlow()
        ){ movieById , favoriteMovieList ->
            return@combine if (movieById != null) {
                 UiMovieDetailModel(
                    movie = movieById ,
                    isFavorite = favoriteMovieList.map { it.id }.contains(movieById.id)
                    )
            } else {
                null
            }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){uiModel ->
            if (uiModel == null) return@observe

            dismissProgressBar()

            binding.progressOnPoster.isVisible=true
            binding.ivMovie.load(uiModel.movie.poster){
                listener { request, result ->
                    binding.progressOnPoster.isGone=true
                }
            }

            binding.tvMovieName.text= uiModel.movie.title
            binding.tvIMDB.text= uiModel.movie.imdb_rating
            binding.tvYear.text= uiModel.movie.year
            binding.tvRate.text= uiModel.movie.rated
            binding.tvCountry.text= uiModel.movie.country
            binding.tvDirector.text= uiModel.movie.director
            binding.tvGenres.text= convertListToText(uiModel.movie.genres)
            binding.tvActors.text= uiModel.movie.actors
            binding.tvPlot.text= uiModel.movie.plot

            val imageRes=if (uiModel.isFavorite) {
                R.drawable.ic_round_favorite_24
            } else {
                R.drawable.ic_round_favorite_border_24
            }

            binding.favoriteImage.load(imageRes)


            if (uiModel.movie.genres.isNotEmpty()){
                val genreId =genreNameToId(uiModel.movie.genres.component1())
                viewModel.getMovieByGenre(genreId)
                viewModel.movieByGenreLiveData.observe(viewLifecycleOwner){movieByGenre->
                    controller.setData(uiModel.movie , movieByGenre)
                    binding.imageEpoxyRecyclerView.setController(controller)
                }
            }



            binding.favoriteImage.setOnClickListener {
                if (uiModel.isFavorite){
                    roomViewModel.deleteFavoriteMovie(
                        FavoriteMovieEntity(
                            id = uiModel.movie.id ,
                            title = uiModel.movie.title
                        )
                    )
                }else{
                    roomViewModel.insertFavoriteMovie(
                        FavoriteMovieEntity(
                            id = uiModel.movie.id,
                            title = uiModel.movie.title
                        )
                    )
                }
            }



        }



        viewModel.movieByIdLiveData.observe(viewLifecycleOwner){movieById->
            dismissProgressBar()

            binding.progressOnPoster.isVisible=true
            binding.ivMovie.load(movieById?.poster){
                listener { request, result ->
                    binding.progressOnPoster.isGone=true
                }
            }

            binding.tvMovieName.text=movieById?.title
            binding.tvIMDB.text= movieById?.imdb_rating
            binding.tvYear.text= movieById?.year
            binding.tvRate.text= movieById?.rated
            binding.tvCountry.text= movieById?.country
            binding.tvDirector.text= movieById?.director
            binding.tvGenres.text= movieById?.genres.toString()
            binding.tvActors.text= movieById?.actors
            binding.tvPlot.text= movieById?.plot


            if (movieById?.genres?.isNotEmpty() == true){
                val genreId =genreNameToId(movieById.genres.component1())
                viewModel.getMovieByGenre(genreId)
                viewModel.movieByGenreLiveData.observe(viewLifecycleOwner){movieByGenre->
                    controller.setData(movieById,movieByGenre)
                    binding.imageEpoxyRecyclerView.setController(controller)
                }
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
            "Family" -> 13
            "War" -> 14
            "Animation" -> 15
            "Romance" -> 16
            "Horror" -> 17
            "Music" -> 18
            "Film-Noir" -> 19
            "Musical" -> 20
            "Sport" ->21
            else -> 0
        }
    }


    private fun convertListToText(list : List<String>): String{
        val sb = StringBuilder()
        for (element in list) {
            sb.append(element).append(", ")
        }
        return sb.toString()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}