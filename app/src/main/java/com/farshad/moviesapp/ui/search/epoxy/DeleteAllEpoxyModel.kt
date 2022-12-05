package com.farshad.moviesapp.ui.search.epoxy

import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelDeleteAllBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

data class DeleteAllEpoxyModel (
    val onDeleteAlClick : () -> Unit
        ): ViewBindingKotlinModel<ModelDeleteAllBinding>(R.layout.model_delete_all) {
    override fun ModelDeleteAllBinding.bind() {
        cardView.setOnClickListener { onDeleteAlClick() }
    }
}