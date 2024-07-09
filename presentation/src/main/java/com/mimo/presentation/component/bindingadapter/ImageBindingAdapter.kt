package com.mimo.presentation.component.bindingadapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mimo.presentation.BuildConfig
import com.mimo.presentation.R

@BindingAdapter("setImage")
fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(BuildConfig.MIMO_SERVER_URL + BuildConfig.MIMO_POST_THUMBNAIL_BASE_URL + url)
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
