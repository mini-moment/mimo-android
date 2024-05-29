package com.mimo.android.presentation.videodetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimo.android.databinding.ItemPostBinding
import com.mimo.android.databinding.ItemTagBinding
import com.mimo.android.domain.model.PostData

class PostListAdapter : ListAdapter<PostData, PostItemViewHolder>(diffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PostData>() {
            override fun areItemsTheSame(
                oldItem: PostData,
                newItem: PostData,
            ): Boolean {
                return oldItem.markerId == newItem.markerId
            }

            override fun areContentsTheSame(
                oldItem: PostData,
                newItem: PostData,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class PostItemViewHolder(
    private val binding: ItemPostBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PostData) {
        with(binding) {
            post = item
        }
    }
}
