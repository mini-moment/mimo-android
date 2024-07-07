package com.mimo.android.presentation.component.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mimo.android.R
import com.mimo.android.domain.model.HashTag
import com.mimo.android.presentation.upload_video.VideoThumbnail
import com.mimo.android.presentation.component.adapter.TagListAdapter
import com.mimo.android.presentation.component.adapter.ThumbNailAdapter

@BindingAdapter("chips")
fun RecyclerView.bindChips(items: List<HashTag>) {
    if (this.adapter != null) {
        (this.adapter as TagListAdapter).submitList(items.toMutableList())
    }
}

@BindingAdapter("selectedChips", "clickEvent")
fun ChipGroup.bindSelectedChips(items: List<HashTag>, click: () -> Unit) {
    removeAllViews()
    items.forEach { tag ->
        Chip(context).apply {
            text = tag.name
            letterSpacing = (-0.07).toFloat()
            isCloseIconVisible = true
            setChipIconResource(R.drawable.ic_hash_tag)
            setChipBackgroundColorResource(R.color.splash_background)
            setChipStrokeColorResource(R.color.black)
            chipIconSize = 40f
            chipStrokeWidth = 5f
            setOnCloseIconClickListener {
                tag.isSelected = false
                removeView(this)
                click.invoke()
            }
            addView(this)
        }
    }
}

@BindingAdapter("app:thumbnails")
fun RecyclerView.bindThumbnails(items: List<VideoThumbnail>) {
    if (this.adapter != null) {
        (this.adapter as ThumbNailAdapter).submitList(items.toMutableList())
    }
}
