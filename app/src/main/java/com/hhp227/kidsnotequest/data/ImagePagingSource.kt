package com.hhp227.kidsnotequest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hhp227.kidsnotequest.api.MainService
import com.hhp227.kidsnotequest.utilities.STARTING_PAGE_INDEX
import kotlinx.coroutines.delay

class ImagePagingSource(private val mainService: MainService) : PagingSource<Int, Image>() {
    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            delay(1000)
            LoadResult.Page(
                data = mainService.searchImages(page, params.loadSize),
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (params.key == null) page + 3 else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}