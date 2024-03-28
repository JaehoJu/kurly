package com.jj.kurly.core.data.repository

import com.jj.kurly.core.data.model.asDomainModel
import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.SectionInfo
import com.jj.kurly.core.network.NetworkDataSource
import com.jj.kurly.core.network.model.SectionDto
import com.jj.kurly.core.network.model.ProductDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SectionRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : SectionRepository {

    override fun getSectionInfos(): Flow<List<SectionInfo>> {
        return flow {
            emit(
                networkDataSource.getSections()
                    .map(SectionDto::asDomainModel)
            )
        }
    }

    override fun getProducts(sectionId: Int): Flow<List<Product>> {
        return flow {
            emit(
                networkDataSource.getSectionProducts(sectionId)
                    .map(ProductDto::asDomainModel)
            )
        }
    }
}