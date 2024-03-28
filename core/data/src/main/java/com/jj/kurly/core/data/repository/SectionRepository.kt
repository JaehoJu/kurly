package com.jj.kurly.core.data.repository

import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.SectionInfo
import kotlinx.coroutines.flow.Flow

interface SectionRepository {

    fun getSectionInfos(): Flow<List<SectionInfo>>

    fun getProducts(sectionId: Int): Flow<List<Product>>
}