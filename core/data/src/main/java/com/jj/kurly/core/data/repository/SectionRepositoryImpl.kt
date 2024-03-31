package com.jj.kurly.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jj.kurly.core.data.model.asDomainModel
import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.SectionInfo
import com.jj.kurly.core.network.NetworkDataSource
import com.jj.kurly.core.network.model.ProductDto
import com.jj.kurly.core.network.model.SectionDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SectionRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : SectionRepository {

    override fun getSectionInfos(): Flow<PagingData<SectionInfo>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                SectionPagingSource(networkDataSource)
            }
        ).flow.map { it.map(SectionDto::asDomainModel) }
    }

    override suspend fun getProducts(sectionId: Int): List<Product> {
        return networkDataSource.getSectionProducts(sectionId).map(ProductDto::asDomainModel)
    }
}