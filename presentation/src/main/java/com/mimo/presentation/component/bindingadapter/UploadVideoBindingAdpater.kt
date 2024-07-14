package com.mimo.presentation.component.bindingadapter

import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mimo.domain.model.HashTag
import com.mimo.presentation.R
import com.mimo.presentation.component.adapter.TagListAdapter
import com.mimo.presentation.component.adapter.ThumbNailAdapter
import com.mimo.presentation.upload_video.VideoThumbnail

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

@BindingAdapter(value = ["app:topic", "app:isThumbnailLoading", "app:videoUrl"])
fun AppCompatButton.bindEnabled(topic: String, isThumbnailLoading: Boolean, videoUrl: String) {
    this.isEnabled = topic.isNotBlank() && isThumbnailLoading.not() && videoUrl.isNotBlank()
}
