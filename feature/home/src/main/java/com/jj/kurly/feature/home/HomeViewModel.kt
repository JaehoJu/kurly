package com.jj.kurly.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jj.kurly.core.domain.GetSectionsUseCase
import com.jj.kurly.core.domain.GetUserDataUseCase
import com.jj.kurly.core.domain.SetProductWishedUseCase
import com.jj.kurly.core.model.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSectionsUseCase: GetSectionsUseCase,
    getUserDataUseCase: GetUserDataUseCase,
    private val setProductWishedUseCase: SetProductWishedUseCase,
) : ViewModel() {

    private val wishedProducts: Flow<Set<Int>> = getUserDataUseCase().map { it.wishedProducts }

    private val _sectionsPagingData: Flow<PagingData<Section>> = getSectionsUseCase()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    val sectionPagingData = combine(
        _sectionsPagingData,
        wishedProducts
    ) { pagingData, wishedProducts ->
        pagingData.map {
            it.appendIsWished(wishedProducts)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PagingData.empty()
    )

    private fun Section.appendIsWished(
        wishedProducts: Set<Int>
    ): Section {
        return copy(
            products = products.map { wishableProduct ->
                wishableProduct.copy(
                    isWished = wishableProduct.product.id in wishedProducts
                )
            }
        )
    }

    fun wishProduct(productId: Int, isWished: Boolean) {
        viewModelScope.launch {
            setProductWishedUseCase(productId, isWished)
        }
    }
}