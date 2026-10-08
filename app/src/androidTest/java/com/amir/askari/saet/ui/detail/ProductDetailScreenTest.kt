package com.amir.askari.saet.ui.detail

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.amir.askari.saet.shared.domain.ProductSize
import com.amir.askari.saet.testing.testProduct
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductDetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsTitleColourAndPrice() {
        val state = ProductDetailUiState.Content(
            testProduct(title = "Speed Leggings", colour = "Navy", price = 1000),
        )

        composeRule.setContent { ProductDetailScreen(state = state, onBack = {}, onRetry = {}) }

        composeRule.onNodeWithText("Speed Leggings").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Navy").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("£1,000").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun showsAllSizesAndMarksSoldOutOnes() {
        val state = ProductDetailUiState.Content(
            testProduct(sizes = listOf(ProductSize("XS", inStock = true), ProductSize("S", inStock = false))),
        )

        composeRule.setContent { ProductDetailScreen(state = state, onBack = {}, onRetry = {}) }

        composeRule.onNodeWithText("XS").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithContentDescription("S, sold out").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun rendersDescriptionTextFromHtml() {
        val state = ProductDetailUiState.Content(
            testProduct(descriptionHtml = "<p><strong>RUN WITH IT</strong></p><p>- High-waisted<br>- Compressive fit</p>"),
        )

        composeRule.setContent { ProductDetailScreen(state = state, onBack = {}, onRetry = {}) }

        composeRule.onNodeWithText("RUN WITH IT", substring = true).performScrollTo().assertIsDisplayed()
        composeRule.onAllNodesWithText("<", substring = true).assertCountEquals(0)
    }

    @Test
    fun showsTheErrorAndTryAgainCallsOnRetry() {
        var retried = false

        composeRule.setContent {
            ProductDetailScreen(state = ProductDetailUiState.Error, onBack = {}, onRetry = { retried = true })
        }
        composeRule.onNodeWithText("Try again").performClick()

        composeRule.onNodeWithText("Couldn't load this product").assertIsDisplayed()
        assertTrue(retried)
    }

    @Test
    fun clickingBackCallsOnBack() {
        var wentBack = false

        composeRule.setContent {
            ProductDetailScreen(state = ProductDetailUiState.Loading, onBack = { wentBack = true }, onRetry = {})
        }
        composeRule.onNodeWithContentDescription("Back").performClick()

        assertTrue(wentBack)
    }
}
