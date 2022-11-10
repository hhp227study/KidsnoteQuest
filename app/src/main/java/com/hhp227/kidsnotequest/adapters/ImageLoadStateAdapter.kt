package com.hhp227.kidsnotequest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hhp227.kidsnotequest.databinding.ItemLoadStateBinding

class ImageLoadStateAdapter(
    private val onRetryListener: () -> Unit
) : LoadStateAdapter<ImageLoadStateAdapter.LoadStateItemViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateItemViewHolder {
        return LoadStateItemViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRetryListener
        )
    }

    inner class LoadStateItemViewHolder(
        private val binding: ItemLoadStateBinding,
        private val onRetryListener: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.isLoading = loadState is LoadState.Loading
            binding.retryCallback = onRetryListener
            binding.errorMessage = (loadState as? LoadState.Error)?.error?.message

            binding.executePendingBindings()
        }
    }
}