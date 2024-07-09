package com.mimo.android.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mimo.android.databinding.ItemTagBinding
import com.mimo.domain.model.HashTag


class TagListAdapter : ListAdapter<HashTag, TagListViewHolder>(DiffUtilCallback<HashTag>()) {

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

    fun bind(item: HashTag) {
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
    fun onClick(item: HashTag)
}
