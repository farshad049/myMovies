package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelHeaderTitleBinding


data class HeaderEpoxyModel(val title:String)
    :ViewBindingKotlinModel<ModelHeaderTitleBinding>(R.layout.model_header_title){
    override fun ModelHeaderTitleBinding.bind() {
        tvHeaderTitle.text=title
    }
    //let this to take whole span count
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}