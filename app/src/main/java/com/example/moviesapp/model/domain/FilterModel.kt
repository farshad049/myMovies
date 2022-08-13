package com.example.moviesapp.model.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class FilterModel(
    val country: MutableList<String> = ArrayList(),
    val genres: MutableList<String> = ArrayList(),
    val imdb_rating: Int=0,
    val year: Int =0,
):Parcelable
