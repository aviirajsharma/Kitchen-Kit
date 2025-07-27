package com.avirajsharma.recipeapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
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

@Composable
fun RecipeApp(repository: RecipeRepository){
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        RecipeNavigation(
            navController = navController,
            repository = repository,
            modifier = Modifier.padding(paddingValues)
        )
    }
}