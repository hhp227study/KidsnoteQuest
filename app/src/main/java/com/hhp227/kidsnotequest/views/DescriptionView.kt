package com.hhp227.kidsnotequest.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hhp227.kidsnotequest.databinding.ViewDescriptionBinding

class DescriptionView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var binding: ViewDescriptionBinding

    fun setDescription(title: String, description: String) {
        binding.tvTitle.text = title
        binding.tvDescription.text = description
    }

    init {
        binding = ViewDescriptionBinding.inflate(LayoutInflater.from(context))
    }
}