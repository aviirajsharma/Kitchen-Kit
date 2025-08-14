package com.avirajsharma.recipeapp.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val contentDescription: String = label
) {
    object Home : BottomNavItem(
        route = "recipe_list",
        icon = Icons.Default.MenuBook,
        label = "Recipes",
        contentDescription = "Navigate to recipe list"
    )
    object Search : BottomNavItem(
        route = "chatai",
        icon = Icons.Default.SmartToy,
        label = "ChefMateAI",
        contentDescription = "Navigate to AI chat assistant"
    )
}
