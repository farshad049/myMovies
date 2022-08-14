package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelTitleBinding

data class HeaderEpoxyModel(val title:String)
    :ViewBindingKotlinModel<ModelTitleBinding>(R.layout.model_title){
    override fun ModelTitleBinding.bind() {
        tvHeaderTitle.text=title
    }
    //let this to take whole span count
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}