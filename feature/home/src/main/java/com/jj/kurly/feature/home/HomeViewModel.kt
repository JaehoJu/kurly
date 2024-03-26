package com.jj.kurly.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = getFakeSections()
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
}