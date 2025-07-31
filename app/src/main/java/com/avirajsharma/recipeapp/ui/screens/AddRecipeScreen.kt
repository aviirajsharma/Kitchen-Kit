package com.avirajsharma.recipeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avirajsharma.recipeapp.database.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    onSaveClick: (Recipe) -> Unit,
    onBackClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var cookingTime by remember { mutableStateOf("") }
    var videoUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Recipe") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Recipe Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cookingTime,
                onValueChange = { cookingTime = it },
                label = { Text("Cooking Time") },
                placeholder = { Text("e.g., 30 mins") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients") },
                placeholder = { Text("Enter each ingredient on a new line") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )

            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Instructions") },
                placeholder = { Text("Enter cooking instructions") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 6
            )

            OutlinedTextField(
                value = videoUrl,
                onValueChange = { videoUrl = it },
                label = { Text("Youtube Video Link") },
                placeholder = { Text("Copy Paste URL of the Recipe Youtube Video") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if(title.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty()){
                        val recipe = Recipe(
                            id = 0,
                            title = title,
                            ingredients = ingredients.split("\n").filter { it.isNotBlank() },
                            instructions = instructions,
                            cookingTime = cookingTime.ifBlank { "Not Specified" },
                            videoUrl = videoUrl
                        )
                        onSaveClick(recipe)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && ingredients.isNotBlank() && instructions.isNotBlank()
            ) {
                Text("Save Recipe")
            }
        }
    }
}