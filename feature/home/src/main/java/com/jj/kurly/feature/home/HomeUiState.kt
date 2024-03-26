package com.jj.kurly.feature.home

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val sections: List<SectionInfo>) : HomeUiState
}