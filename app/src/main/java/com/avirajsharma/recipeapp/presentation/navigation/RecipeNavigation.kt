package com.avirajsharma.recipeapp.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
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
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType }),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            RecipeDetailScreen(
                recipeId = recipeId,
                onBackClick = { navController.popBackStack() },
                onEditClick = { navController.navigate("update_recipe/$recipeId") }
            )
        }

        composable("add_recipe") {
            AddRecipeScreen(
                onSaveClick = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
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
                onUpdateClick = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}