package com.jj.kurly.core.data.repository

import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.SectionInfo
import com.jj.kurly.core.model.SectionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class SectionRepositoryImpl @Inject constructor() : SectionRepository {

    override fun getSectionInfos(): Flow<List<SectionInfo>> {
        val sectionInfo = SectionInfo(
            title = "title",
            id = 1,
            type = SectionType.VERTICAL,
            url = "section_products_1"
        )
        return flowOf(
            listOf(
                sectionInfo.copy(type = SectionType.VERTICAL),
                sectionInfo.copy(type = SectionType.HORIZONTAL),
                sectionInfo.copy(type = SectionType.GRID),
                sectionInfo.copy(type = SectionType.HORIZONTAL),
                sectionInfo.copy(type = SectionType.VERTICAL)
            )
        )
    }

    override fun getProducts(url: String): Flow<List<Product>> {
        val product = Product(
            id = 1,
            name = "name",
            image = "url",
            originalPrice = 1,
            discountedPrice = 1,
            isSoldOut = false
        )
        return flowOf(
            listOf(
                product.copy(
                    id = 1,
                    image = "https://img-cf.kurly.com/shop/data/goods/1657692098340l0.jpg",
                    discountedPrice = 100,
                    originalPrice = 100
                ),
                product.copy(
                    id = 2,
                    image = "https://img-cf.kurly.com/shop/data/goods/1648206780555l0.jpeg",
                    discountedPrice = 89,
                    originalPrice = 100
                ),
            )
        )
    }
}