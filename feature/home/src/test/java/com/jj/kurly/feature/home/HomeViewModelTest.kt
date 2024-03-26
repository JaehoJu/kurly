package com.jj.kurly.feature.home

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeViewModelTest {

    private val viewModel = HomeViewModel()

    @Test
    fun initialUiState_isLoading() = runTest {
        assertEquals(HomeUiState.Loading, viewModel.homeUiState.value)
    }
}