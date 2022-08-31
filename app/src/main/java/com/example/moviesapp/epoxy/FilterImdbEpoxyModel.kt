package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelFilterBinding
import com.example.moviesapp.model.ui.UiImdbRateFilter

data class FilterImdbEpoxyModel(
    val uiFilter: UiImdbRateFilter,
    val onFilterClick:(Double) -> Unit
)
    : ViewBindingKotlinModel<ModelFilterBinding>(R.layout.model_filter){
    override fun ModelFilterBinding.bind() {
        tvFilterName.text="more than ${uiFilter.filterDisplayName}"
        root.setOnClickListener { onFilterClick(uiFilter.filterDisplayName) }
        checkbox.isClickable=false
        checkbox.isChecked = uiFilter.isSelected
    }

}
