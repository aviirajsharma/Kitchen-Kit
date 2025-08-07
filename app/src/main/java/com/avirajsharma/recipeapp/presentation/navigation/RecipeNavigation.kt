package com.avirajsharma.recipeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.avirajsharma.recipeapp.presentation.screens.AddRecipeScreen
import com.avirajsharma.recipeapp.presentation.screens.ChatAIScreen
import com.avirajsharma.recipeapp.presentation.screens.RecipeDetailScreen
import com.avirajsharma.recipeapp.presentation.screens.RecipeListScreen
import com.avirajsharma.recipeapp.presentation.screens.UpdateRecipeScreen

@Composable
fun RecipeNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "recipe_list",
        modifier = modifier
    ) {
        composable("recipe_list") {
            RecipeListScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate("recipe_detail/$recipeId")
                },
                onAddRecipeClick = {
                    navController.navigate("add_recipe")
                }
            )
        }


        composable(
            "recipe_detail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            RecipeDetailScreen(
                recipeId = recipeId,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = { navController.navigate("update_recipe/$recipeId") },
            )
        }


        composable("add_recipe") {
            AddRecipeScreen(
                onSaveClick = {
                    // Add recipe to the list and navigate back
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("chatai") {
            ChatAIScreen()
        }
        composable(
            "update_recipe/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            UpdateRecipeScreen(
                recipeId = recipeId,
                onUpdateClick = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}