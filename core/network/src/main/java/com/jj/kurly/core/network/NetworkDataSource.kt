package com.jj.kurly.core.network

import com.jj.kurly.core.network.model.SectionDto
import com.jj.kurly.core.network.model.ProductDto

interface NetworkDataSource {

    suspend fun getSections(): List<SectionDto>

    suspend fun getSectionProducts(sectionId: Int): List<ProductDto>
}