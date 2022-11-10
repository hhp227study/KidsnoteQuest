package com.hhp227.kidsnotequest.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hhp227.kidsnotequest.data.Image
import com.hhp227.kidsnotequest.data.MainRepository
import com.hhp227.kidsnotequest.utilities.PAYLOAD_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: MainRepository
) : ViewModel() {
    val payload: LiveData<Image> get() = savedStateHandle.getLiveData(PAYLOAD_KEY)

    val pagingData: LiveData<PagingData<Image>> = repository.fetchImages(DEFAULT_PAGE_SIZE)
        .cachedIn(viewModelScope)
        .asLiveData()

    private fun setPayload(image: Image) {
        savedStateHandle[PAYLOAD_KEY] = image
    }

    fun postFavorite(item: Image) {
        viewModelScope.launch {
            setPayload(item)
        }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 5
    }
}