package com.mimo.android.presentation.component.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mimo.android.R
import com.mimo.android.domain.model.PostData
import com.mimo.android.presentation.component.adapter.PostListAdapter

@BindingAdapter("app:posts")
fun ViewPager2.bindPosts(items: List<PostData>) {
    if (this.adapter != null) {
        (this.adapter as PostListAdapter).submitList(items.toMutableList())
    }
}

@BindingAdapter("app:userProfileImage")
fun ImageView.bindUserProfileImage(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

@BindingAdapter("app:postHashTags")
fun ChipGroup.bindPostHashTags(tagList: List<PostData.Tag>) {
    removeAllViews()
    tagList.forEach { tag ->
        Chip(context).apply {
            text = tag.name
            setChipIconResource(R.drawable.ic_hash_tag)
            chipIconSize = 30f
            setChipBackgroundColorResource(R.color.splash_logo_message_color)
            setChipStrokeColorResource(R.color.black)
            addView(this)
        }
    }
}
