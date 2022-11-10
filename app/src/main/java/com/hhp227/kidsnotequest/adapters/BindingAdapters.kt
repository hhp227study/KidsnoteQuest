package com.hhp227.kidsnotequest.adapters

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hhp227.kidsnotequest.R
import com.hhp227.kidsnotequest.data.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.load(imageUrl) {
            crossfade(150)
            placeholder(R.drawable.ic_launcher_background_popup_image)
            error(R.drawable.ic_white_import_img_noimg)
        }
    }
}

@BindingAdapter("submitData")
fun submitData(v: RecyclerView, data: PagingData<Image>?) {
    if (data != null && data != PagingData.empty<Image>()) {
        CoroutineScope(Dispatchers.Main).launch {
            ((v.adapter as? ConcatAdapter)?.adapters?.first() as? ImagePagingAdapter)?.submitData(data)
        }
    }
}

@BindingAdapter("payload")
fun bindPayload(v: RecyclerView, payload: Image?) {
    val concatAdapter = v.adapter as? ConcatAdapter

    (concatAdapter?.adapters?.first() as? ImagePagingAdapter)?.also { adapter ->
        payload?.also(adapter::updateFavorite)
    }
}

@BindingAdapter("isEmpty")
fun isEmpty(v: View, data: PagingData<Image>?) {
    v.isVisible = data == null || data == PagingData.empty<Image>()
}
/*
@BindingAdapter(value = ["title", "description"])
fun bindDesc(v: DescriptionView, title: String, description: String) {
    v.setDescription(title, description)
}*/