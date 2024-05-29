package com.mimo.android.presentation.video.upload

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mimo.android.R
import com.mimo.android.domain.model.TagData

@BindingAdapter("chips")
fun RecyclerView.bindChips(items: List<TagData>) {
    if (this.adapter != null) {
        (this.adapter as TagListAdapter).submitList(items.toMutableList())
    }
}

@BindingAdapter("selectedChips", "clickEvent")
fun ChipGroup.bindSelectedChips(items: List<TagData>, click: () -> Unit) {
    removeAllViews()
    items.forEach { tag ->
        Chip(context).apply {
            text = tag.name
            isCloseIconVisible = true
            setChipIconResource(R.drawable.ic_hash_tag)
            setChipBackgroundColorResource(R.color.splash_background)
            setChipStrokeColorResource(R.color.black)
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
