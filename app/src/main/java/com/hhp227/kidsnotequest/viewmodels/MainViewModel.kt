package com.hhp227.kidsnotequest.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hhp227.kidsnotequest.data.Image
import com.hhp227.kidsnotequest.data.MainRepository
import com.hhp227.kidsnotequest.utilities.PAYLOAD_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: MainRepository
) : ViewModel() {
    val pagingData: LiveData<PagingData<Image>> = repository.fetchImages().cachedIn(viewModelScope).asLiveData()

    val payload: LiveData<Image> get() = savedStateHandle.getLiveData(PAYLOAD_KEY)

    private fun setPayload(image: Image) {
        savedStateHandle[PAYLOAD_KEY] = image
    }

    fun postFavorite(item: Image) {
        viewModelScope.launch {
            setPayload(item)
        }
    }
}