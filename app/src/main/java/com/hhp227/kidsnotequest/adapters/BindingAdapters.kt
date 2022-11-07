package com.hhp227.kidsnotequest.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hhp227.kidsnotequest.data.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("submitData")
fun submitData(v: RecyclerView, list: PagingData<Image>) {
    CoroutineScope(Dispatchers.Main).launch {
        (v.adapter as? ImagePagingAdapter)?.submitData(list)
    }
}