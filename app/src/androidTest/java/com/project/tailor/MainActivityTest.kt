package com.project.tailor

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.project.tailor.ui.home.HomeScreen
import com.project.tailor.ui.theme.TailorTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainActivityTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun myTest() {
        // Start the app
//        composeTestRule.setContent {
//            TailorTheme() {
//                HomeScreen(uiState = fakeUiState, /*...*/)
//            }
//        }
//
//        composeTestRule.onNodeWithText("Continue").performClick()
//
//        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
}