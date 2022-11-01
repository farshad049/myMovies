package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelExpandableListBinding
import com.farshad.moviesapp.databinding.ModelHeaderTitleBinding


data class HeaderExpandableEpoxyModel(
    val selectedExpandTitle:String ,
    val onClick : (String) -> Unit
) :ViewBindingKotlinModel<ModelExpandableListBinding>(R.layout.model_expandable_list){
    override fun ModelExpandableListBinding.bind() {
        tvHeaderTitle.text=selectedExpandTitle
        root.setOnClickListener { onClick(selectedExpandTitle) }

    }
    //let this to take whole span count
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}