package com.farshad.moviesapp.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelDeleteAllBinding

data class DeleteAllEpoxyModel (
    val onDeleteAlClick : () -> Unit
        ):ViewBindingKotlinModel<ModelDeleteAllBinding>(R.layout.model_delete_all) {
    override fun ModelDeleteAllBinding.bind() {
        cardView.setOnClickListener { onDeleteAlClick() }
    }
}