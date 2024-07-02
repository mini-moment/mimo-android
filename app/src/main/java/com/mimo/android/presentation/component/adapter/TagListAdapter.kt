package com.mimo.android.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mimo.android.databinding.ItemTagBinding
import com.mimo.android.domain.model.TagData

class TagListAdapter : ListAdapter<TagData, TagListViewHolder>(DiffUtilCallback<TagData>()) {

    private var tagClickListener: TagClickListener? = null

    fun setTagClickListener(listener: TagClickListener) {
        tagClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagListViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagListViewHolder(binding, tagClickListener)
    }

    override fun onBindViewHolder(holder: TagListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TagListViewHolder(
    private val binding: ItemTagBinding,
    private val tagClickListener: TagClickListener?,
) :
    ViewHolder(binding.root) {

    fun bind(item: TagData) {
        with(binding) {
            tag = item
            chipHashTag.setOnClickListener {
                item.isSelected = true
                tagClickListener?.onClick(item)
            }
        }
    }
}

interface TagClickListener {
    fun onClick(item: TagData)
}
