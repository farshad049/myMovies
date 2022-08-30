package com.example.moviesapp.epoxy

import androidx.core.content.ContextCompat
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelFilterBinding
import com.example.moviesapp.model.domaind.UiFilter

data class FilterEpoxyModel(
    val uiFilter:UiFilter,
    val onFilterClick:(String) -> Unit
    )
    :ViewBindingKotlinModel<ModelFilterBinding>(R.layout.model_filter){
    override fun ModelFilterBinding.bind() {
        tvFilterName.text=uiFilter.filterDisplayName
        root.setOnClickListener { onFilterClick(uiFilter.filterDisplayName) }

        val cardBackgroundColor= if (uiFilter.isSelected) R.color.teal_200 else R.color.white

        root.setCardBackgroundColor(ContextCompat.getColor(root.context , cardBackgroundColor))

    }

}
