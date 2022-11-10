package com.hhp227.kidsnotequest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hhp227.kidsnotequest.data.Image
import com.hhp227.kidsnotequest.utilities.ARRAY_KEY
import com.hhp227.kidsnotequest.utilities.IMAGE_KEY
import com.hhp227.kidsnotequest.utilities.POSITION_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val image: LiveData<Image> get() = savedStateHandle.getLiveData(IMAGE_KEY)

    val currentPosition: LiveData<Int> get() = savedStateHandle.getLiveData(POSITION_KEY)

    val array: LiveData<Array<Image>?> get() = savedStateHandle.getLiveData(ARRAY_KEY)

    fun onFavoriteClick() {
        if (savedStateHandle.contains(IMAGE_KEY)) {
            val image = savedStateHandle.get<Image>(IMAGE_KEY)?.apply { isFavorite = !isFavorite }
            savedStateHandle[IMAGE_KEY] = image
        }
    }
}