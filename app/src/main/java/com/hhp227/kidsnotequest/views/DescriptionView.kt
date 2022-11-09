package com.hhp227.kidsnotequest.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hhp227.kidsnotequest.databinding.ViewDescriptionBinding
import com.hhp227.kidsnotequest.R

class DescriptionView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    private var binding: ViewDescriptionBinding

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.view_description, this, true)
        binding = ViewDescriptionBinding.bind(view)
    }

    fun setDescription(title: String, description: String) {
        binding.tvTitle.text = title
        binding.tvDescription.text = description
    }
}