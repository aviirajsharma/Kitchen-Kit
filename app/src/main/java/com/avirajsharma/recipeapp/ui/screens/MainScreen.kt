package com.avirajsharma.recipeapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.avirajsharma.recipeapp.RecipeRepository
import com.avirajsharma.recipeapp.ui.composables.BottomNavigationBar
import com.avirajsharma.recipeapp.ui.navigation.RecipeNavigation

@Composable
fun RecipeApp(repository: RecipeRepository){
    val navController = rememberNavController()

    Scaffold (
        bottomBar = { BottomNavigationBar(navController = navController) }
    ){ paddingValues ->
        RecipeNavigation(
            navController = navController,
            repository = repository,
            modifier = Modifier.padding(paddingValues)
        )
    }
}