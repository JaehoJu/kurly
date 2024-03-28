package com.jj.kurly.core.domain

import com.jj.kurly.core.data.repository.SectionRepository
import com.jj.kurly.core.data.repository.UserDataRepository
import com.jj.kurly.core.model.WishableProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetWishableProductsUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val sectionRepository: SectionRepository
) {
    operator fun invoke(sectionId: Int): Flow<List<WishableProduct>> {
        return combine(
            sectionRepository.getProducts(sectionId),
            userDataRepository.userData
        ) { products, userData ->
            products.map { product ->
                WishableProduct(
                    product = product,
                    isWished = product.id in userData.wishedProducts
                )
            }
        }
    }
}