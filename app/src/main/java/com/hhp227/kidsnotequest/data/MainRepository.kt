package com.hhp227.kidsnotequest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hhp227.kidsnotequest.api.MainService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val apiService: MainService) {
    fun searchImage() = Pager(
        config = PagingConfig(100, enablePlaceholders = false),
        pagingSourceFactory = fun() = ImagePagingSource(apiService)
    ).flow
}