package com.avirajsharma.recipeapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.presentation.viewmodel.UpdateRecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateRecipeScreen(
    recipeId: Int,
    onUpdateClick: () -> Unit,
    onBackClick: () -> Unit

) {
    val viewModel: UpdateRecipeViewModel = hiltViewModel()
    
    val recipe by viewModel.recipe.collectAsState()

    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var cookingTime by remember { mutableStateOf("") }
    var videoUrl by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isDataLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    LaunchedEffect(recipe) {
        recipe?.let { currentRecipe ->
            if (!isDataLoaded) {
                title = currentRecipe.title
                ingredients = currentRecipe.ingredients.joinToString("\n")
                instructions = currentRecipe.instructions
                cookingTime = currentRecipe.cookingTime
                videoUrl = currentRecipe.videoUrl
                isDataLoaded = true
            }
        }
    }

    recipe?.let { currentRecipe ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Update Recipe") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Recipe Title") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    singleLine = true
                )

                OutlinedTextField(
                    value = cookingTime,
                    onValueChange = { cookingTime = it },
                    label = { Text("Cooking Time") },
                    placeholder = { Text("e.g., 30 mins") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    singleLine = true
                )

                OutlinedTextField(
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    label = { Text("Ingredients") },
                    placeholder = { Text("Enter each ingredient on a new line") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    minLines = 4,
                    maxLines = 8
                )

                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Instructions") },
                    placeholder = { Text("Enter cooking instructions") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    minLines = 6,
                    maxLines = 10
                )

                OutlinedTextField(
                    value = videoUrl,
                    onValueChange = { videoUrl = it },
                    label = { Text("Youtube Video Link") },
                    placeholder = { Text("Copy Paste URL of the Recipe Youtube Video") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    singleLine = true
                )

                Button(
                    onClick = {
                        if (title.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty()) {
                            isLoading = true
                            val updatedRecipe = Recipe(
                                id = currentRecipe.id,
                                title = title.trim(),
                                ingredients = ingredients.split("\n")
                                    .map { it.trim() }
                                    .filter { it.isNotBlank() },
                                instructions = instructions.trim(),
                                cookingTime = cookingTime.trim().ifBlank { "Not Specified" },
                                videoUrl = videoUrl.trim()
                            )
                            viewModel.updateRecipe(updatedRecipe) {
                                isLoading = false
                                onUpdateClick()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank() &&
                            ingredients.isNotBlank() &&
                            instructions.isNotBlank() &&
                            !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text("Update Recipe")
                }
            }
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}