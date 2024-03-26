package com.jj.kurly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jj.kurly.feature.home.navigation.HOME_ROUTE
import com.jj.kurly.feature.home.navigation.homeScreen

@Composable
fun KurlyNavHost(
    navController: NavHostController,
    startDestination: String = HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen()
    }
}