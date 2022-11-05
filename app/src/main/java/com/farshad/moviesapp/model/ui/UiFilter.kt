package com.farshad.moviesapp.model.ui

data class UiFilter (
    val isExpand : Boolean,
    val filterInfo : FilterInfo
        )

{
    data class FilterInfo(
        val filterDisplayName:String,
        val isSelected:Boolean
    )

}