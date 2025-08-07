package com.avirajsharma.recipeapp.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.avirajsharma.recipeapp.presentation.composables.BottomNavigationBar
import com.avirajsharma.recipeapp.presentation.navigation.RecipeNavigation

@Composable
fun RecipeApp(){
    val navController = rememberNavController()

    Scaffold (
        bottomBar = { BottomNavigationBar(navController = navController) }
    ){ paddingValues ->
        RecipeNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}