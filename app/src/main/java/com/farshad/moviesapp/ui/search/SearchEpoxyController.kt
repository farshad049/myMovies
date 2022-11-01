package com.farshad.moviesapp.ui.search

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.farshad.moviesapp.R
import com.farshad.moviesapp.ViewModelAndRepository.search.SearchDataSource
import com.farshad.moviesapp.databinding.ModelExeptionErrorBinding
import com.farshad.moviesapp.epoxy.LoadingEpoxyModel
import com.farshad.moviesapp.epoxy.MovieListModelSearchFragment
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel
import com.farshad.moviesapp.model.domain.DomainMovieModel

class SearchEpoxyController(
    private val onMovieClick:(Int , String)-> Unit
):PagingDataEpoxyController<DomainMovieModel>() {

    var localException:SearchDataSource.LocalException?=null
        set(value) {
            field=value
            if (localException !=null){
                //if localException is not null .immediately run "addModels" block of code,otherwise it will run "buildItemModel"
                requestForcedModelBuild()
            }
        }



    override fun buildItemModel(currentPosition: Int, item: DomainMovieModel?): EpoxyModel<*> {
        return MovieListModelSearchFragment(item!!,onMovieClick)
            .id("search${item.id}")
    }



    override fun addModels(models: List<EpoxyModel<*>>) {

        if (localException != null){
            //show error information
            LocalExceptionErrorEpoxyModel(localException!!).id("error_state").addTo(this)
            return
        }


        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading3").addTo(this)
            return
        }

        super.addModels(models)
    }







    data class LocalExceptionErrorEpoxyModel(val localException:SearchDataSource.LocalException)
        :ViewBindingKotlinModel<ModelExeptionErrorBinding>(R.layout.model_exeption_error){
        override fun ModelExeptionErrorBinding.bind() {
            tvErrorTitle.text=localException.title
            tvErrorDescription.text=localException.description
        }
        //let this to take whole span count
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }





}