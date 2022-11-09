package com.hhp227.kidsnotequest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hhp227.kidsnotequest.api.MainService
import kotlinx.coroutines.delay

class ImagePagingSource(private val mainService: MainService) : PagingSource<Int, Image>() {
    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val page = params.key ?: 0
        return try {
            delay(2000)
            LoadResult.Page(
                data = mainService.searchImages(page, params.loadSize),
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}