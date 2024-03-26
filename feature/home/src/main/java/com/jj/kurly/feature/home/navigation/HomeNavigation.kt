package com.jj.kurly.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jj.kurly.feature.home.HomeScreenRoute

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeScreen() {
    composable(
        route = HOME_ROUTE
    ) {
        HomeScreenRoute()
    }
}