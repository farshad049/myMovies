package com.farshad.moviesapp.ui.submitMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.moviesapp.data.model.ui.SubmitFieldValidationModel
import com.farshad.moviesapp.data.model.ui.SubmitResponseModel
import com.farshad.moviesapp.data.model.ui.TextFieldStatusModel
import com.farshad.moviesapp.data.model.ui.UploadMovieModel
import com.farshad.moviesapp.data.repository.SubmitMovieRepository
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
    private val eventChannel = Channel<SubmitResponseModel>()
    val submitFlow = eventChannel.receiveAsFlow()

//    private val _submitMovieBase64LiveData= MutableLiveData<UploadMovieModel?>()
//    val pushMovieBase64LiveData: LiveData<UploadMovieModel?> = _submitMovieBase64LiveData


    private val _validationMutableLiveData= MutableStateFlow<SubmitFieldValidationModel>(SubmitFieldValidationModel())
    val validationLiveData: StateFlow<SubmitFieldValidationModel> = _validationMutableLiveData




    fun pushMovieBase64(movie: UploadMovieModel){
        viewModelScope.launch {
            val response=repository.pushMovieBase64(movie)
            //_submitMovieBase64LiveData.postValue(response)
            eventChannel.send(response)
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
                    SubmitFieldValidationModel(
                        title = TextFieldStatusModel.Error("* please enter a valid title")
                    )
            }
            imdbIdB.isEmpty() -> {
                _validationMutableLiveData.value=
                    SubmitFieldValidationModel(
                        imdbId = TextFieldStatusModel.Error("* please enter a valid IMDB ID")
                    )
            }
            countryB.isEmpty() -> {
                _validationMutableLiveData.value =
                    SubmitFieldValidationModel(
                        country = TextFieldStatusModel.Error("* please enter a valid country name")
                    )
            }
            yearB.isEmpty() && year.length < 4 -> {
                _validationMutableLiveData.value=
                    SubmitFieldValidationModel(
                        year = TextFieldStatusModel.Error("* please enter a valid year")
                    )
            }else ->{
                _validationMutableLiveData.value = SubmitFieldValidationModel(
                    title = TextFieldStatusModel.Success(),
                    imdbId = TextFieldStatusModel.Success(),
                    country = TextFieldStatusModel.Success(),
                    year  = TextFieldStatusModel.Success(),
                    poster  = TextFieldStatusModel.Success()
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