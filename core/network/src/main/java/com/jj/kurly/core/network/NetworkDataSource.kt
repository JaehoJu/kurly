package com.jj.kurly.core.network

import com.jj.kurly.core.network.model.ProductDto
import com.jj.kurly.core.network.model.ResponseDto
import com.jj.kurly.core.network.model.SectionDto

interface NetworkDataSource {

    suspend fun getSections(page: Int): ResponseDto<List<SectionDto>>

    suspend fun getSectionProducts(sectionId: Int): List<ProductDto>
}