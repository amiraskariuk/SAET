package com.amir.askari.saet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amir.askari.saet.ui.detail.ProductDetailDestination
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
            ProductDetailDestination(onBack = { navController.navigateUp() })
        }
    }
}
