package com.example.disney


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