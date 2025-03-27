package com.kurnavova.spacedout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kurnavova.spacedout.navigation.MainNavHost
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

/**
 * Main activity.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpacedOutTheme {
               MainNavHost()
            }
        }
    }
}
