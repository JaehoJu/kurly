package com.jj.kurly

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.jj.kurly.navigation.KurlyNavHost
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    private val startScreen = "Start Screen"

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            KurlyNavHost(navController = navController)
        }
    }

    @Test
    fun firstScreen_isHome() {
        composeTestRule.apply {
            onNodeWithContentDescription(startScreen).assertIsDisplayed()
        }
    }
}