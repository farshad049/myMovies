package com.farshad.moviesapp.ui.submitMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.repository.SubmitMovieRepository
import com.farshad.moviesapp.ui.submitMovie.model.SubmitFieldValidationModel
import com.farshad.moviesapp.ui.submitMovie.model.SubmitResponseModel
import com.farshad.moviesapp.ui.submitMovie.model.UploadMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubmitBase64ViewModel @Inject constructor(
    private val repository: SubmitMovieRepository
) : ViewModel() {

    //works like event
    private val _submitMovieBase64 = Channel<SubmitResponseModel>()
    val submitMovieBase64 = _submitMovieBase64.receiveAsFlow()


    private val _validationMutableLiveData= MutableStateFlow<SubmitFieldValidationModel>(
        SubmitFieldValidationModel()
    )
    val validationLiveData: StateFlow<SubmitFieldValidationModel> = _validationMutableLiveData




    fun pushMovieBase64(movie: UploadMovieModel){
        viewModelScope.launch {
            val response=repository.pushMovieBase64(movie)
            _submitMovieBase64.send(response)
        }
    }




    fun validate(
        title : String,
        imdb_id : String,
        country : String,
        year : String ,
        poster : String = ""
    ){

        val titleB = title.trim()
        val imdbIdB = imdb_id.trim()
        val countryB = country.trim()
        val yearB = year.trim()

        when{
            titleB.isEmpty() -> {
                _validationMutableLiveData.value=
                    SubmitFieldValidationModel(title = "* please enter a valid title" )
                return
            }
            imdbIdB.isEmpty() -> {
                _validationMutableLiveData.value=
                    SubmitFieldValidationModel(imdbId = "* please enter a valid IMDB ID")
                return
            }
            countryB.isEmpty() -> {
                _validationMutableLiveData.value =
                    SubmitFieldValidationModel(country = "* please enter a valid country name")
                return
            }
            yearB.isEmpty() && year.length < 4 -> {
                _validationMutableLiveData.value =
                    SubmitFieldValidationModel(year = "* please enter a valid year")
                return

            }else ->{
                _validationMutableLiveData.value = SubmitFieldValidationModel(
                    title = null,
                    imdbId = null,
                    country = null,
                    year  = null,
                    poster  = null,
                )

              pushMovieBase64(
                  UploadMovieModel(
                      title = titleB,
                      imdb_id = imdbIdB ,
                      country = countryB,
                      year = yearB.toInt(),
                      poster = poster
                  )
              )

            }
        }

    }
















}