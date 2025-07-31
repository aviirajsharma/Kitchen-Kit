package com.avirajsharma.recipeapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.avirajsharma.recipeapp.database.RecipeDatabase
import com.avirajsharma.recipeapp.ui.screens.RecipeApp
import com.avirajsharma.recipeapp.ui.theme.RecipeAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var database: RecipeDatabase
    private lateinit var repository: RecipeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize database and repository
        database = RecipeDatabase.getDataBase(this)
        repository = RecipeRepository.getInstance(database.recipeDao())
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                RecipeApp(repository = repository)
            }
        }
    }
}

