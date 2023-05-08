package com.farshad.moviesapp.ui.favorite.epoxy

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import coil.load
import com.farshad.moviesapp.R
import com.farshad.moviesapp.databinding.ModelCircleImageBinding
import com.farshad.moviesapp.epoxy.ViewBindingKotlinModel

data class CircleImageEpoxyModel(
    val imageUrl: String,
    val isSelected: Boolean ,
    val onClick: ()-> Unit
) : ViewBindingKotlinModel<ModelCircleImageBinding>(R.layout.model_circle_image) {
    override fun ModelCircleImageBinding.bind() {

        ivCircle.load(imageUrl)
        root.setOnClickListener { onClick() }

        val strokeColor = if (isSelected) {
            ContextCompat.getColor(root.context, R.color.teal_700)
        } else {
            ContextCompat.getColor(root.context, R.color.purple_200)
        }
        ivCircle.strokeColor = ColorStateList.valueOf(strokeColor)

        if (isSelected) {
            AnimatorSet().apply {
                val animator = ValueAnimator.ofFloat(1f, 0.85f, 1.05f, 1f).apply {
                    addUpdateListener {
                        ivCircle.scaleX = it.animatedValue as Float
                        ivCircle.scaleY = it.animatedValue as Float
                    }
                    duration = 250L
                    start()
                }
                play(animator)
            }
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as CircleImageEpoxyModel

        if (imageUrl != other.imageUrl) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }


}