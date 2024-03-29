package com.jj.kurly.core.domain

import com.jj.kurly.core.data.repository.SectionRepository
import com.jj.kurly.core.data.repository.UserDataRepository
import com.jj.kurly.core.model.Product
import com.jj.kurly.core.model.Section
import com.jj.kurly.core.model.SectionInfo
import com.jj.kurly.core.model.WishableProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val sectionRepository: SectionRepository
) {
    operator fun invoke(): Flow<List<Section>> {
        return combine(
            userDataRepository.userData,
            getSectionPairs(),
        ) { userData, pairs ->
            pairs.map { (info, products) ->
                Section(
                    info = info,
                    products = products.mapToWishableProduct(userData.wishedProducts)
                )
            }
        }
    }

    private fun getSectionPairs(): Flow<List<Pair<SectionInfo, List<Product>>>> {
        return sectionRepository.getSectionInfos().map { infos ->
            infos.map { info ->
                sectionRepository.getProducts(info.id).map { products ->
                    info to products
                }.first()
            }
        }
    }

    private fun List<Product>.mapToWishableProduct(wishedProducts: Set<Int>): List<WishableProduct> {
        return map { product ->
            WishableProduct(
                product = product,
                isWished = product.id in wishedProducts
            )
        }
    }
}