package com.kurnavova.spacedout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kurnavova.spacedout.navigation.MainNavHost
import com.kurnavova.spacedout.ui.theme.SpacedOutTheme

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

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    SpacedOutTheme {
        MainNavHost()
    }
}
