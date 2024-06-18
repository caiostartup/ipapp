package com.hyqoo.ipapp.ui.view

import androidx.activity.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hyqoo.ipapp.ui.IpDataAppState
import com.hyqoo.ipapp.ui.Screen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun IpAppApp(
    appState: IpDataAppState,
    modifier: Modifier = Modifier
) {
    val adaptiveInfo = currentWindowAdaptiveInfo()

    NavHost(
        navController = appState.navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { backStackEntry ->
            MainScreen(
                windowSizeClass = adaptiveInfo.windowSizeClass,
            )
        }
    }
}