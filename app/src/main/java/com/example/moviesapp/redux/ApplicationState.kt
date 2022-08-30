package com.example.mystore.redux




data class ApplicationState(
    val productFilterInfo : FilterInfo= FilterInfo(),
)

{
    data class FilterInfo(
        val genres : Set<String> = setOf("Crime","Drama","Action","Biography","History","Adventure","Fantasy","Western","Comedy","Sci-Fi",
            "Mystery","Thriller","Family","War","Animation","Romance","Horror","Music","Film-Noir","Musical","Sport"),
        val selectedGenres : Set<String> = emptySet()
    )
}
