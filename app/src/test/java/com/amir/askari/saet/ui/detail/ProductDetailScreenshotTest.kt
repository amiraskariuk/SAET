package com.amir.askari.saet.ui.detail

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.amir.askari.saet.testing.screenshotDetailProduct
import com.amir.askari.saet.ui.theme.SAETTheme
import org.junit.Rule
import org.junit.Test

class ProductDetailScreenshotTest {

    private val tallPixel5 = DeviceConfig.PIXEL_5.copy(screenHeight = 3600)

    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = tallPixel5)

    @Test
    fun contentLight() {
        paparazzi.snapshot {
            SAETTheme(darkTheme = false) {
                ProductDetailScreen(ProductDetailUiState.Content(screenshotDetailProduct), onBack = {}, onRetry = {})
            }
        }
    }

    @Test
    fun contentDark() {
        paparazzi.snapshot {
            SAETTheme(darkTheme = true) {
                ProductDetailScreen(ProductDetailUiState.Content(screenshotDetailProduct), onBack = {}, onRetry = {})
            }
        }
    }

    @Test
    fun contentLargeFont() {
        paparazzi.unsafeUpdateConfig(deviceConfig = tallPixel5.copy(fontScale = 1.5f))

        paparazzi.snapshot {
            SAETTheme(darkTheme = false) {
                ProductDetailScreen(ProductDetailUiState.Content(screenshotDetailProduct), onBack = {}, onRetry = {})
            }
        }
    }
}
