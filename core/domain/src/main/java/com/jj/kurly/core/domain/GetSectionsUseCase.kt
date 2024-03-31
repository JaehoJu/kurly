package com.jj.kurly.core.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.jj.kurly.core.data.repository.SectionRepository
import com.jj.kurly.core.model.Section
import com.jj.kurly.core.model.WishableProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val sectionRepository: SectionRepository
) {
    operator fun invoke(): Flow<PagingData<Section>> {
        return sectionRepository.getSectionInfos().map {
            it.map { info ->
                val products = sectionRepository.getProducts(info.id).map { product ->
                    WishableProduct(product = product, isWished = false) // false 일괄 초기화 이후 값 다시 설정
                }
                Section(info, products)
            }
        }
    }
}