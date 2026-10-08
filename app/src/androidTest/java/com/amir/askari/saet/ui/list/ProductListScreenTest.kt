package com.amir.askari.saet.ui.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.amir.askari.saet.shared.domain.LabelStyle
import com.amir.askari.saet.shared.domain.ProductLabel
import com.amir.askari.saet.testing.testProduct
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsTheLoadingIndicator() {
        val state = ProductListUiState.Loading

        composeRule.setContent { ProductListScreen(state = state, onRetry = {}, onProductClick = {}) }

        composeRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun showsTitleColourAndPriceForEachProduct() {
        val state = ProductListUiState.Content(
            listOf(
                testProduct(id = 1, title = "Training Leggings", colour = "Navy", price = 50),
                testProduct(id = 2, title = "Flex Leggings", colour = "Black", price = 1000),
            ),
        )

        composeRule.setContent { ProductListScreen(state = state, onRetry = {}, onProductClick = {}) }

        composeRule.onNodeWithText("Training Leggings").assertIsDisplayed()
        composeRule.onNodeWithText("Navy").assertIsDisplayed()
        composeRule.onNodeWithText("£50").assertIsDisplayed()
        composeRule.onNodeWithText("Flex Leggings").assertIsDisplayed()
        composeRule.onNodeWithText("Black").assertIsDisplayed()
        composeRule.onNodeWithText("£1,000").assertIsDisplayed()
    }

    @Test
    fun showsLabelBadges() {
        val state = ProductListUiState.Content(
            listOf(testProduct(labels = listOf(ProductLabel("Going fast", LabelStyle.Urgent)))),
        )

        composeRule.setContent { ProductListScreen(state = state, onRetry = {}, onProductClick = {}) }

        composeRule.onNodeWithText("Going fast").assertIsDisplayed()
    }

    @Test
    fun showsSoldOutForAnOutOfStockProduct() {
        val state = ProductListUiState.Content(listOf(testProduct(inStock = false)))

        composeRule.setContent { ProductListScreen(state = state, onRetry = {}, onProductClick = {}) }

        composeRule.onNodeWithText("Sold out").assertIsDisplayed()
    }

    @Test
    fun showsTheErrorAndTryAgainCallsOnRetry() {
        var retried = false

        composeRule.setContent {
            ProductListScreen(state = ProductListUiState.Error, onRetry = { retried = true }, onProductClick = {})
        }
        composeRule.onNodeWithText("Try again").performClick()

        composeRule.onNodeWithText("Couldn't load products").assertIsDisplayed()
        assertTrue(retried)
    }

    @Test
    fun clickingAProductCallsOnProductClickWithItsId() {
        var clickedId: Long? = null
        val state = ProductListUiState.Content(listOf(testProduct(id = 42, title = "Training Leggings")))

        composeRule.setContent {
            ProductListScreen(state = state, onRetry = {}, onProductClick = { clickedId = it })
        }
        composeRule.onNodeWithText("Training Leggings").performClick()

        assertEquals(42L, clickedId)
    }

    @Test
    fun showsTheEmptyMessageForAnEmptyList() {
        val state = ProductListUiState.Content(emptyList())

        composeRule.setContent { ProductListScreen(state = state, onRetry = {}, onProductClick = {}) }

        composeRule.onNodeWithText("No products to show right now").assertIsDisplayed()
    }
}
