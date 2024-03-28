package com.jj.kurly.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.kurly.core.domain.GetSectionsUseCase
import com.jj.kurly.core.domain.SetProductWishedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSectionsUseCase: GetSectionsUseCase,
    private val setProductWishedUseCase: SetProductWishedUseCase,
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = getSectionsUseCase()
        .map(HomeUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    fun wishProduct(productId: Int, isWished: Boolean) {
        viewModelScope.launch {
            setProductWishedUseCase(productId, isWished)
        }
    }
}