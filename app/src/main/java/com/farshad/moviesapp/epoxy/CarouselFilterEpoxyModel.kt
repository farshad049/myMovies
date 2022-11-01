package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelCaruselFilterBinding

data class CarouselFilterEpoxyModel (
        val filterName : String,
        val onFilterDeleteClick : (String) -> Unit
        ):ViewBindingKotlinModel<ModelCaruselFilterBinding>(R.layout.model_carusel_filter) {
        override fun ModelCaruselFilterBinding.bind() {
                tvFilterName.text= filterName
                root.setOnClickListener { onFilterDeleteClick(filterName) }
        }
}