package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelFilterBinding
import com.farshad.moviesapp.data.model.ui.UiFilter

data class FilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterClick:(String) -> Unit
    )
    :ViewBindingKotlinModel<ModelFilterBinding>(R.layout.model_filter){
    override fun ModelFilterBinding.bind() {

        tvFilterName.text=uiFilter.filterDisplayName
        root.setOnClickListener { onFilterClick(uiFilter.filterDisplayName) }
        //checkbox.isClickable=false
        checkbox.isChecked = uiFilter.isSelected
    }

}
