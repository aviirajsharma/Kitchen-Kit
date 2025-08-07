package com.avirajsharma.recipeapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.avirajsharma.recipeapp.data.local.database.RecipeDatabase
import com.avirajsharma.recipeapp.domain.repository.RecipeRepository
import com.avirajsharma.recipeapp.presentation.screens.RecipeApp
import com.avirajsharma.recipeapp.presentation.theme.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                RecipeApp()
            }
        }
    }
}

