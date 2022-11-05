package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelFilterBinding
import com.farshad.moviesapp.model.ui.UiFilter

data class FilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterClick:(String) -> Unit
    )
    :ViewBindingKotlinModel<ModelFilterBinding>(R.layout.model_filter){
    override fun ModelFilterBinding.bind() {

        tvFilterName.text=uiFilter.filterInfo.filterDisplayName
        root.setOnClickListener { onFilterClick(uiFilter.filterInfo.filterDisplayName) }
        //checkbox.isClickable=false
        checkbox.isChecked = uiFilter.filterInfo.isSelected
    }

}
