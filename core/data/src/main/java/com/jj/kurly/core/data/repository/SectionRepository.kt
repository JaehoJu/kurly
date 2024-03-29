package com.jj.kurly.core.data.repository

import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.SectionInfo

interface SectionRepository {

    suspend fun getSectionInfos(): List<SectionInfo>

    suspend fun getProducts(sectionId: Int): List<Product>
}