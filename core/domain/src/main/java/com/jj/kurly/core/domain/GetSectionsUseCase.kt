package com.jj.kurly.core.domain

import com.jj.kurly.core.data.repository.SectionRepository
import com.jj.kurly.core.model.Section
import com.jj.kurly.core.model.WishableProduct
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val sectionRepository: SectionRepository
) {
    suspend operator fun invoke(): List<Section> {
        return sectionRepository.getSectionInfos().map { info ->
            val products = sectionRepository.getProducts(info.id).map {
                WishableProduct(product = it, isWished = false)
            }
            Section(info, products)
        }
    }
}