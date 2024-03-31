package com.jj.kurly.core.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jj.kurly.core.network.NetworkDataSource
import com.jj.kurly.core.network.model.SectionDto
import java.io.IOException

internal class SectionPagingSource(
    private val networkDataSource: NetworkDataSource
) : PagingSource<Int, SectionDto>() {
    override fun getRefreshKey(state: PagingState<Int, SectionDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SectionDto> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = networkDataSource.getSections(nextPageNumber)
            LoadResult.Page(
                data = response.data,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.data.isEmpty()) null else response.paging?.nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}