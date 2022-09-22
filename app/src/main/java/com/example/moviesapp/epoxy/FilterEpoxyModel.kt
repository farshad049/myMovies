package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelFilterBinding
import com.example.moviesapp.model.ui.UiGenreFilter

data class FilterEpoxyModel(
    val uiFilter: UiGenreFilter,
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
