package com.mimo.presentation.component.bindingadapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mimo.domain.model.Post
import com.mimo.presentation.R
import com.mimo.presentation.component.adapter.PostListAdapter

@BindingAdapter("app:posts")
fun ViewPager2.bindPosts(items: List<Post>) {
    if (this.adapter != null) {
        (this.adapter as PostListAdapter).submitList(items.toMutableList())
    }
}

@BindingAdapter("app:postIndex")
fun ViewPager2.bindPostIndex(index: Int) {
    this.setCurrentItem(index, false)
}

@BindingAdapter("app:userProfileImage")
fun ImageView.bindUserProfileImage(url: String) {
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .circleCrop()
        .placeholder(ContextCompat.getDrawable(this.context, R.drawable.ic_user_selected))
        .into(this)
}

@BindingAdapter("app:postHashTags")
fun ChipGroup.bindPostHashTags(tagList: List<Post.Tag>) {
    removeAllViews()
    tagList.forEach { tag ->
        Chip(context).apply {
            text = tag.name
            letterSpacing = (-0.07).toFloat()
            setChipIconResource(R.drawable.ic_hash_tag)
            chipIconSize = 30f
            setChipBackgroundColorResource(R.color.splash_logo_message_color)
            setChipStrokeColorResource(R.color.white)
            chipStrokeWidth = 1f
            addView(this)
        }
    }
}
