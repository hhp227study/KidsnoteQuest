package com.hhp227.kidsnotequest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.kidsnotequest.data.Image
import com.hhp227.kidsnotequest.databinding.ItemImageBinding
import javax.inject.Inject

class ImagePagingAdapter @Inject constructor() : PagingDataAdapter<Image, ImagePagingAdapter.ItemHolder>(ImageDiffCallback()) {
    private lateinit var onItemClickListener: OnItemClickListener

    val loadState: LiveData<CombinedLoadStates> get() = loadStateFlow.asLiveData()

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        payloads.forEach { payload ->
            if (payload is Image) {
                getItem(position)?.let {
                    it.isFavorite = !payload.isFavorite

                    holder.bind(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun updateFavorite(payload: Image) {
        snapshot().items.indexOfFirst { item ->
            item.id == payload.id
        }.also { position ->
            if (position >= 0) {
                notifyItemChanged(position, payload)
            }
        }
    }

    inner class ItemHolder(
        private val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image?) {
            binding.image = item
            binding.position = bindingAdapterPosition
            binding.onItemClickListener = onItemClickListener

            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: Image)

        fun onLikeClick(item: Image)
    }
}

private class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }
}