package com.jj.kurly.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.kurly.core.domain.GetSectionsUseCase
import com.jj.kurly.core.domain.GetUserDataUseCase
import com.jj.kurly.core.domain.SetProductWishedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSectionsUseCase: GetSectionsUseCase,
    getUserDataUseCase: GetUserDataUseCase,
    private val setProductWishedUseCase: SetProductWishedUseCase,
) : ViewModel() {

    private val wishedProducts: Flow<Set<Int>> = getUserDataUseCase().map { it.wishedProducts }

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> =
        combine(_homeUiState, wishedProducts) { homeUiState, wishedProducts ->
            (homeUiState as? HomeUiState.Success)?.let {
                appendIsWished(it, wishedProducts)
            } ?: homeUiState
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    init {
        refresh()
    }

    private fun appendIsWished(
        homeUiState: HomeUiState.Success,
        wishedProducts: Set<Int>
    ): HomeUiState.Success {
        return homeUiState.copy(
            sections = homeUiState.sections.map { section ->
                section.copy(
                    products = section.products.map { wishableProduct ->
                        wishableProduct.copy(
                            isWished = wishableProduct.product.id in wishedProducts
                        )
                    }
                )
            }
        )
    }

    fun wishProduct(productId: Int, isWished: Boolean) {
        viewModelScope.launch {
            setProductWishedUseCase(productId, isWished)
        }
    }

    fun refresh() {
        _homeUiState.value = HomeUiState.Loading
        viewModelScope.launch {
            val sections = getSectionsUseCase()
            _homeUiState.value = HomeUiState.Success(sections)
        }
    }
}