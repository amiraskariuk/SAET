package com.amir.askari.saet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.amir.askari.saet.ui.navigation.AppNavHost
import com.amir.askari.saet.ui.theme.SAETTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SAETTheme {
                AppNavHost()
            }
        }
    }
}
