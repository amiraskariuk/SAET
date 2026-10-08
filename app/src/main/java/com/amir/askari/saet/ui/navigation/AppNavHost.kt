package com.amir.askari.saet.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amir.askari.saet.R
import com.amir.askari.saet.ui.list.ProductListDestination

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ProductListRoute) {
        composable<ProductListRoute> {
            ProductListDestination(
                onProductClick = { productId -> navController.navigate(ProductDetailRoute(productId)) },
            )
        }
        composable<ProductDetailRoute> {
            Box(
                modifier = Modifier.fillMaxSize().safeDrawingPadding(),
                contentAlignment = Alignment.Center,
            ) {
                Text(stringResource(R.string.product_detail_placeholder))
            }
        }
    }
}
