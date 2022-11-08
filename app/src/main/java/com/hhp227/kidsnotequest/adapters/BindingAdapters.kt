package com.hhp227.kidsnotequest.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hhp227.kidsnotequest.data.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.load(imageUrl) {
            crossfade(150)
            //placeholder(R.drawable.ic_launcher_background)
            //error(R.drawable.ic_launcher_background)
        }
    }
}

@BindingAdapter("submitData")
fun submitData(v: RecyclerView, list: PagingData<Image>) {
    CoroutineScope(Dispatchers.Main).launch {
        ((v.adapter as? ConcatAdapter)?.adapters?.first() as? ImagePagingAdapter)?.submitData(list)
    }
}