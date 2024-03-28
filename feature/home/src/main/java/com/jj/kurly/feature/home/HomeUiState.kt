package com.jj.kurly.feature.home

import com.jj.kurly.core.model.Section

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val sections: List<Section>) : HomeUiState
}