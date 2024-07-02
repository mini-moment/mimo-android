package com.mimo.android.presentation.component.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mimo.android.R
import com.mimo.android.domain.model.PostData

@BindingAdapter("app:postHashTags")
fun ChipGroup.bindHashTags(tagList: List<PostData.Tag>) {
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
