package com.hhp227.kidsnotequest.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hhp227.kidsnotequest.data.Image
import com.hhp227.kidsnotequest.data.MainRepository
import com.hhp227.kidsnotequest.data.NetworkStatus
import com.hhp227.kidsnotequest.utilities.NETWORK_STATUS_KEY
import com.hhp227.kidsnotequest.utilities.PAYLOAD_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: MainRepository
) : ViewModel() {
    val payload: LiveData<Image> get() = savedStateHandle.getLiveData(PAYLOAD_KEY)

    val networkStatus: LiveData<NetworkStatus> get() = savedStateHandle.getLiveData(NETWORK_STATUS_KEY)

    val pagingData: LiveData<PagingData<Image>> = MutableLiveData(PagingData.empty())//repository.fetchImages().cachedIn(viewModelScope).asLiveData()

    val pData: LiveData<PagingData<Image>> = networkStatus.switchMap {
        if (it == NetworkStatus.Available) {
            repository.fetchImages().cachedIn(viewModelScope).asLiveData()
        } else {
            MutableLiveData(PagingData.empty())
        }
    }

    private fun setPayload(image: Image) {
        savedStateHandle[PAYLOAD_KEY] = image
    }

    fun postFavorite(item: Image) {
        viewModelScope.launch {
            setPayload(item)
        }
    }
}