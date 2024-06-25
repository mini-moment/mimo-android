package com.mimo.android.presentation.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimo.android.databinding.ItemClusterBinding
import com.mimo.android.domain.model.MarkerData
import com.mimo.android.domain.model.PostData

class MapClusterAdapter : ListAdapter<PostData, MapClusterAdapter.MapClusterViewHolder>(
    diffUtil
) {

    private var onItemClickListener: ((PostData) -> Unit)? = null
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
        val binding: ItemClusterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postData: PostData) {
            binding.apply {

            }
        }
    }

    fun onItemClickListener(listener: (PostData) -> Unit) {
        this.onItemClickListener = listener
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<PostData>() {
            override fun areItemsTheSame(
                oldItem: PostData,
                newItem: PostData,
            ): Boolean {
                return oldItem.id == newItem.id
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