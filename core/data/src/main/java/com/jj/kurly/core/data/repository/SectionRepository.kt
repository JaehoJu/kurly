package com.jj.kurly.core.data.repository

import androidx.paging.PagingData
import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.Section
import com.jj.kurly.core.model.SectionInfo
import kotlinx.coroutines.flow.Flow

interface SectionRepository {

    fun getSectionInfos(): Flow<PagingData<SectionInfo>>

    suspend fun getProducts(sectionId: Int): List<Product>
}