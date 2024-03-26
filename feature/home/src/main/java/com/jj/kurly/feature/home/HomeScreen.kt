package com.jj.kurly.feature.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
internal fun HomeScreenRoute() {
    HomeScreen()
}

@Composable
internal fun HomeScreen() {
    Text(
        text = "home",
        modifier = Modifier.semantics { contentDescription = "Start Screen" }
    )
}