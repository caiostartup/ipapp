package com.hyqoo.ipapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * List of screens for [IpDataApp]
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home")

    // Adicionaria as outras telas para navegação
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    IpDataAppState(navController)
}

class IpDataAppState(
    val navController: NavHostController
) {
    fun navigateBack() {
        navController.popBackStack()
    }

}