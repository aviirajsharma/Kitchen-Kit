package com.avirajsharma.recipeapp.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("recipe_list", Icons.Default.Home, "Home")
    object Search : BottomNavItem("chatai", Icons.Default.Search, "ChatAI")
}
