package com.mimo.android.presentation.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mimo.android.databinding.ItemMyPostBinding
import com.mimo.android.domain.model.PostData
import com.mimo.android.domain.model.toTagData

class MyPostAdapter : ListAdapter<PostData, MyPostAdapter.MyPostViewHolder>(
    DiffUtilCallback<PostData>(),
) {

    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val binding = ItemMyPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        holder.bind(getItem(position), Pair(position + 1, itemCount))
        holder.binding.ivPlayer.setOnClickListener {
            onItemClickListener?.let {
                it(position)
            }
        }
    }

    class MyPostViewHolder(
        val binding: ItemMyPostBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postData: PostData, value: Pair<Int, Int>) {
            binding.apply {
                this.postData = postData
                this.indicator = "${value.first} / ${value.second}"
                val tagListAdapter = TagListAdapter()
                rcTagList.adapter = tagListAdapter
                tagListAdapter.submitList(postData.toTagData())
            }
        }
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        this.onItemClickListener = listener
    }
}
