package com.amir.askari.saet.ui.list

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.amir.askari.saet.testing.screenshotProducts
import com.amir.askari.saet.ui.theme.SAETTheme
import org.junit.Rule
import org.junit.Test

class ProductListScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

    @Test
    fun contentLight() {
        paparazzi.snapshot {
            SAETTheme(darkTheme = false) {
                ProductListScreen(ProductListUiState.Content(screenshotProducts), onRetry = {}, onProductClick = {})
            }
        }
    }

    @Test
    fun contentDark() {
        paparazzi.snapshot {
            SAETTheme(darkTheme = true) {
                ProductListScreen(ProductListUiState.Content(screenshotProducts), onRetry = {}, onProductClick = {})
            }
        }
    }

    @Test
    fun contentLargeFont() {
        paparazzi.unsafeUpdateConfig(deviceConfig = DeviceConfig.PIXEL_5.copy(fontScale = 1.5f))

        paparazzi.snapshot {
            SAETTheme(darkTheme = false) {
                ProductListScreen(ProductListUiState.Content(screenshotProducts), onRetry = {}, onProductClick = {})
            }
        }
    }

    @Test
    fun error() {
        paparazzi.snapshot {
            SAETTheme(darkTheme = false) {
                ProductListScreen(ProductListUiState.Error, onRetry = {}, onProductClick = {})
            }
        }
    }
}
