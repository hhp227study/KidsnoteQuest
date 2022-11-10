package com.hhp227.kidsnotequest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hhp227.kidsnotequest.data.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val image: LiveData<Image> get() = savedStateHandle.getLiveData("image")

    fun onFavoriteClick() {
        val image = savedStateHandle.get<Image>("image")?.apply {
            isFavorite = !isFavorite
        }
        savedStateHandle["image"] = image
    }

    init {
        val pos = savedStateHandle.get<Int>("position")
        Log.e("TEST", "test: $pos")
        savedStateHandle.get<Array<Image>?>("array")?.also {
            Log.e("TEST", "image: ${it.toList()}")
        }
    }
}