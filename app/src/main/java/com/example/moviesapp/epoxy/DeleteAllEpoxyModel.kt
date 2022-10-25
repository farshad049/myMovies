package com.example.moviesapp.epoxy

import com.example.moviesapp.R
import com.example.moviesapp.databinding.ModelDeleteAllBinding

data class DeleteAllEpoxyModel (
    val onDeleteAlClick : () -> Unit
        ):ViewBindingKotlinModel<ModelDeleteAllBinding>(R.layout.model_delete_all) {
    override fun ModelDeleteAllBinding.bind() {
        cardView.setOnClickListener { onDeleteAlClick() }
    }
}