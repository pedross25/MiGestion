package com.example.migestion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.migestion.ui.navigation.graphs.RootNavigationGraph
import com.example.migestion.ui.theme.MiGestionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiGestionTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}
