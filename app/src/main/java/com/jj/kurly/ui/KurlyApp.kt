package com.jj.kurly.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jj.kurly.navigation.KurlyNavHost

@Composable
fun KurlyApp() {
    val navController = rememberNavController()
    KurlyNavHost(navController = navController)
}