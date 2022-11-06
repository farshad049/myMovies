package com.farshad.moviesapp.ViewModelAndRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.farshad.moviesapp.ViewModelAndRepository.search.SearchDataSource
import com.farshad.moviesapp.model.domain.DomainMovieModel
import com.farshad.moviesapp.model.mapper.MovieMapper
import com.farshad.moviesapp.model.network.UploadMovieModelStringPoster
import com.farshad.moviesapp.network.ApiClient
import com.farshad.moviesapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val apiClient: ApiClient,
    private val movieMapper: MovieMapper
) : ViewModel() {

    private val _movieByIdLiveData= MutableLiveData<DomainMovieModel?>()
    val movieByIdLiveData: LiveData<DomainMovieModel?> =_movieByIdLiveData

    private val _movieByGenreLiveData= MutableLiveData<List<DomainMovieModel?>>()
    val movieByGenreLiveData: LiveData<List<DomainMovieModel?>> = _movieByGenreLiveData

    private val _pushMovieBase64LiveData= MutableLiveData<UploadMovieModelStringPoster?>()
    val pushMovieBase64LiveData: LiveData<UploadMovieModelStringPoster?> = _pushMovieBase64LiveData

    private val _pushMovieMultipartLiveData= MutableLiveData<UploadMovieModelStringPoster?>()
    val pushMovieMultipartLiveData: LiveData<UploadMovieModelStringPoster?> = _pushMovieMultipartLiveData






    fun getMovieById(movieId: Int){
        viewModelScope.launch {
            val response=repository.getMovieById(movieId)
            _movieByIdLiveData.postValue(response)
        }
    }

    fun  getMovieByGenre(genreId: Int){
        viewModelScope.launch {
            val response=repository.getMovieByGenre(genreId)
            _movieByGenreLiveData.postValue(response)
        }
    }

    fun pushMovieBase64(movie:UploadMovieModelStringPoster){
        viewModelScope.launch {
            val response=repository.pushMovieBase64(movie)
            _pushMovieBase64LiveData.postValue(response)
        }
    }

    fun pushMovieMultipart(
        poster: MultipartBody.Part?,
        title: RequestBody,
        imdb_id: RequestBody,
        country: RequestBody,
        year: RequestBody
    ){
        viewModelScope.launch {
            val response=repository.pushMovieMultipart(poster,title,imdb_id,country,year)
            _pushMovieMultipartLiveData.postValue(response)
        }
    }



    var movieDataSource: MovieDataSource? = null
        get() {
            if (field == null || field?.invalid == true){
                field = MovieDataSource(apiClient,movieMapper)
            }
            return field
        }

    val movieListFlow = Pager(PagingConfig(
            pageSize = 10,
            prefetchDistance = 20,
            enablePlaceholders = false
        )
    ) { movieDataSource!! }.flow.cachedIn(viewModelScope)












}