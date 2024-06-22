package com.mimo.android.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimo.android.databinding.ItemClusterBinding
import com.mimo.android.domain.model.MarkerData

class MapClusterAdapter : ListAdapter<MarkerData, MapClusterAdapter.MapClusterViewHolder>(
    diffUtil,
) {

    private var onItemClickListener: ((MarkerData) -> Unit)? = null
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
        fun bind(markerData: MarkerData) {
            binding.apply {
            }
        }
    }

    fun onItemClickListener(listener: (MarkerData) -> Unit) {
        this.onItemClickListener = listener
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MarkerData>() {
            override fun areItemsTheSame(
                oldItem: MarkerData,
                newItem: MarkerData,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MarkerData,
                newItem: MarkerData,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
