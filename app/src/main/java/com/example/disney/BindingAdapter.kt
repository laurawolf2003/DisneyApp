package com.example.disney


import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide // Or your preferred image loading library (e.g., coil.load)

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .into(view)
        // If using Coil:
        // view.load(url)
    }
}
@BindingAdapter("favoriteIcon")
fun setFavoriteIcon(button: ImageButton, isFavorite: Boolean) {
    val drawableRes = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
    button.setImageResource(drawableRes)
}