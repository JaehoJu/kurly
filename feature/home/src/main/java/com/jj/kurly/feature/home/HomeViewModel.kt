package com.jj.kurly.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeUiState: StateFlow<HomeUiState> = getFakeSections()
        .flatMapLatest {
            flowOf(
                it.map { sectionInfo ->
                    Section(
                        info = sectionInfo,
                        products = getFakeProducts(sectionInfo.url).first()
                    )
                }
            )
        }
        .map(HomeUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    private fun getFakeSections(): Flow<List<SectionInfo>> {
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

    private fun getFakeProducts(url: String): Flow<List<Product>> {
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
                product.copy(id = 1, discountedPrice = 100, originalPrice = 100),
                product.copy(id = 2, discountedPrice = 89, originalPrice = 100)
            )
        )
    }
}