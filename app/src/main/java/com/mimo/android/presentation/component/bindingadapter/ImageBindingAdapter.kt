package com.mimo.android.presentation.component.bindingadapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mimo.android.R

@BindingAdapter("setImage")
fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load("http://192.168.0.115:8080/post/thumbnail/$url")
        .centerCrop()
        .placeholder(ContextCompat.getDrawable(imageView.context, R.color.black))
        .into(imageView)
}

@BindingAdapter("profileImageUrl")
fun profileImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .centerCrop()
        .circleCrop()
        .placeholder(ContextCompat.getDrawable(imageView.context, R.drawable.ic_user_selected))
        .into(imageView)
}
