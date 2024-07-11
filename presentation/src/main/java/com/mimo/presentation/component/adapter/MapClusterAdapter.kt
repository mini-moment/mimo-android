package com.mimo.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimo.domain.model.Post
import com.mimo.presentation.databinding.ItemClusterBinding

class MapClusterAdapter : ListAdapter<Post, MapClusterAdapter.MapClusterViewHolder>(
    DiffUtilCallback<Post>(),
) {

    private var onItemClickListener: ((Post) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapClusterViewHolder {
        val binding = ItemClusterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapClusterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapClusterViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(getItem(holder.absoluteAdapterPosition))
            }
        }
    }

    class MapClusterViewHolder(
        val binding: ItemClusterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.postData = post
        }
    }

    fun onItemClickListener(listener: (Post) -> Unit) {
        this.onItemClickListener = listener
    }
}
