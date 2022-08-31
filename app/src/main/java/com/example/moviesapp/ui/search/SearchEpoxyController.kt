package com.example.moviesapp.ui.search

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.moviesapp.R
import com.example.moviesapp.ViewModelAndRepository.search.SearchDataSource
import com.example.moviesapp.databinding.ModelExeptionErrorBinding
import com.example.moviesapp.databinding.ModelMovieListItemBinding
import com.example.moviesapp.epoxy.LoadingEpoxyModel
import com.example.moviesapp.epoxy.MovieListModel
import com.example.moviesapp.epoxy.ViewBindingKotlinModel
import com.example.moviesapp.model.domain.DomainMovieModel

class SearchEpoxyController(
    private val onMovieClick:(Int)-> Unit
):PagingDataEpoxyController<DomainMovieModel>() {

//    var localException:SearchDataSource.LocalException?=null
//        set(value) {
//            field=value
//            if (localException !=null){
//                //if localException is not null .immediately run "addModels" block of code,otherwise it will run "buildItemModel"
//                requestForcedModelBuild()
//            }
//        }



    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return MovieListModel(item!!,onMovieClick)
            .id("search${item.id}")
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

//        if (localException != null){
//            //show error information
//            LocalExceptionErrorEpoxyModel(localException!!).id("error_state").addTo(this)
//            return
//        }


        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading3").addTo(this)
            return
        }

        super.addModels(models)
    }







//    data class LocalExceptionErrorEpoxyModel(val localException:SearchDataSource.LocalException)
//        :ViewBindingKotlinModel<ModelExeptionErrorBinding>(R.layout.model_exeption_error){
//        override fun ModelExeptionErrorBinding.bind() {
//            tvErrorTitle.text=localException.title
//            tvErrorDescription.text=localException.description
//        }
//        //let this to take whole span count
//        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
//            return totalSpanCount
//        }
//
//    }





}