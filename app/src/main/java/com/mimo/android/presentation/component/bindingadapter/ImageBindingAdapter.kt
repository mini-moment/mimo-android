package com.mimo.android.presentation.component.bindingadapter

import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mimo.android.R

@BindingAdapter("setImage")
fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(imageView.context, R.color.black))
        .into(imageView)
}