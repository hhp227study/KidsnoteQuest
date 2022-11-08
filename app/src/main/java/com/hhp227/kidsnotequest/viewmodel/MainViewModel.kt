package com.hhp227.kidsnotequest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.hhp227.kidsnotequest.data.Image
import com.hhp227.kidsnotequest.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val data: LiveData<PagingData<Image>> get() = repository.fetchImages().asLiveData()
}