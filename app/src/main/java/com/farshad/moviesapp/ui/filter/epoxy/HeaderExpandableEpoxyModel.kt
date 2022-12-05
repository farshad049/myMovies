package com.farshad.moviesapp.ui.filter.epoxy

import coil.load
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelExpandableListBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel


data class HeaderExpandableEpoxyModel(
    val selectedExpandTitle:String ,
    val onClick : (String) -> Unit ,
    val isExpand : Boolean = false
) : ViewBindingKotlinModel<ModelExpandableListBinding>(R.layout.model_expandable_list){
    override fun ModelExpandableListBinding.bind() {
        tvHeaderTitle.text=selectedExpandTitle
        root.setOnClickListener { onClick(selectedExpandTitle) }

        val imgRes = if (isExpand) R.drawable.ic_round_keyboard_arrow_down_24 else R.drawable.ic_round_keyboard_arrow_right_24

        ivArrow.load(imgRes)

    }
    //let this to take whole span count
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}