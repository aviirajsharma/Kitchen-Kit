package com.avirajsharma.recipeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.avirajsharma.recipeapp.RecipeRepository
import com.avirajsharma.recipeapp.ui.screens.AddRecipeScreen
import com.avirajsharma.recipeapp.ui.screens.ChatAIScreen
import com.avirajsharma.recipeapp.ui.screens.RecipeDetailScreen
import com.avirajsharma.recipeapp.ui.screens.RecipeListScreen

@Composable
fun RecipeNavigation(
    navController: NavHostController,
    repository: RecipeRepository,
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
                },
                repository = repository
            )
        }


        composable(
            "recipe_detail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")?:0
            RecipeDetailScreen(
                recipeId = recipeId,
                onBackClick = {
                    navController.popBackStack()
                },
                repository = repository
            )
        }


        composable("add_recipe") {
            AddRecipeScreen(
                onSaveClick = { recipe ->
                    // Add recipe to the list and navigate back
                    repository.addRecipe(recipe)
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
    }
}