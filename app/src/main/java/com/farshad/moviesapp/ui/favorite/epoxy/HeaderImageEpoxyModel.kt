package com.farshad.moviesapp.ui.favorite.epoxy

import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelFavoriteHeaderBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

data class HeaderImageEpoxyModel(
    val imageUrl: String,
    val onClick: () -> Unit
): ViewBindingKotlinModel<ModelFavoriteHeaderBinding>(R.layout.model_favorite_header) {

    private var imageDisposable: Disposable? = null


    override fun ModelFavoriteHeaderBinding.bind() {
        root.setOnClickListener { onClick() }
        val loader = ImageLoader(root.context)
        val req = ImageRequest.Builder(root.context)
            .data(imageUrl)
            .allowHardware(false) // Needed for Palette library
            .target { result ->
                val bitmap = (result as BitmapDrawable).bitmap
                val builder = Palette.Builder(bitmap)
                val defaultColor = ContextCompat.getColor(root.context, R.color.purple_700)
                root.setBackgroundColor(builder.generate().getMutedColor(defaultColor))
                cardView.setCardBackgroundColor(builder.generate().getDarkMutedColor(defaultColor))
                ivImage.load(bitmap)
            }
            .build()

        imageDisposable = loader.enqueue(req)
    }


    override fun ModelFavoriteHeaderBinding.unbind() {
        imageDisposable?.dispose()
        imageDisposable = null
    }

}