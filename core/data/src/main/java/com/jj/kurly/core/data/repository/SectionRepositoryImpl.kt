package com.jj.kurly.core.data.repository

import com.jj.kurly.core.data.model.asDomainModel
import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.SectionInfo
import com.jj.kurly.core.network.NetworkDataSource
import com.jj.kurly.core.network.model.ProductDto
import com.jj.kurly.core.network.model.SectionDto
import javax.inject.Inject

internal class SectionRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : SectionRepository {

    override suspend fun getSectionInfos(): List<SectionInfo> {
        return networkDataSource.getSections().map(SectionDto::asDomainModel)
    }

    override suspend fun getProducts(sectionId: Int): List<Product> {
        return networkDataSource.getSectionProducts(sectionId).map(ProductDto::asDomainModel)
    }
}