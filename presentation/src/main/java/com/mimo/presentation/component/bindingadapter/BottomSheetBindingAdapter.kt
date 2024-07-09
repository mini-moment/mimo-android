package com.mimo.presentation.component.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mimo.domain.model.Post
import com.mimo.presentation.R

@BindingAdapter("app:postHashTags")
fun ChipGroup.bindHashTags(tagList: List<Post.Tag>) {
    removeAllViews()
    tagList.forEach { tag ->
        Chip(context).apply {
            text = tag.name
            letterSpacing = (-0.07).toFloat()
            setChipIconResource(R.drawable.ic_hash_tag)
            chipIconSize = 30f
            setChipBackgroundColorResource(R.color.splash_logo_message_color)
            setChipStrokeColorResource(R.color.black)
            addView(this)
        }
    }
}
