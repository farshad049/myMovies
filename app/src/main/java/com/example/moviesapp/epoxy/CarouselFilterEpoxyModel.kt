package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelCaruselFilterBinding

data class CarouselFilterEpoxyModel (
        val filterName : String,
        val onFilterDeleteClick : (String) -> Unit
        ):ViewBindingKotlinModel<ModelCaruselFilterBinding>(R.layout.model_carusel_filter) {
        override fun ModelCaruselFilterBinding.bind() {
                tvFilterName.text= filterName
                root.setOnClickListener { onFilterDeleteClick(filterName) }
        }
}