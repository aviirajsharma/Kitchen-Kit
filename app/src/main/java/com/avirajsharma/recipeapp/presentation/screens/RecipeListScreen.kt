package com.avirajsharma.recipeapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.presentation.composables.TopBar
import com.avirajsharma.recipeapp.presentation.viewmodel.RecipeListViewModel


@Composable
fun RecipeListScreen(
    onRecipeClick: (Int) -> Unit,
    onAddRecipeClick: () -> Unit
) {

    val viewModel: RecipeListViewModel = hiltViewModel()
    val recipes by viewModel.recipe.collectAsState()

    Scaffold(
        topBar = {
            TopBar("Kitchen-Kit")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddRecipeClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Recipe")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id) },
                    deleteRecipe = { viewModel.deleteRecipe(recipe) }
                )
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    onClick: () -> Unit,
    deleteRecipe: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.padding(16.dp).weight(1f)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cooking time: ${recipe.cookingTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = deleteRecipe, modifier = Modifier.padding(8.dp)) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Recipe")
            }
        }
    }
}