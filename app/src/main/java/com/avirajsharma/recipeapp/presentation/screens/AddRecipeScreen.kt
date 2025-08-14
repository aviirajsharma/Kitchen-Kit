package com.avirajsharma.recipeapp.presentation.screens

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.presentation.composables.RecipeTopAppBar
import com.avirajsharma.recipeapp.presentation.viewmodel.AddRecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: AddRecipeViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var title by rememberSaveable { mutableStateOf("") }
    var ingredients by rememberSaveable { mutableStateOf("") }
    var instructions by rememberSaveable { mutableStateOf("") }
    var cookingTime by rememberSaveable { mutableStateOf("") }
    var videoUrl by rememberSaveable { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Form validation states
    var titleError by remember { mutableStateOf<String?>(null) }
    var ingredientsError by remember { mutableStateOf<String?>(null) }
    var instructionsError by remember { mutableStateOf<String?>(null) }

    // Validation functions
    fun validateTitle(value: String): String? {
        return when {
            value.isBlank() -> "Recipe title is required"
            value.length < 3 -> "Title must be at least 3 characters"
            value.length > 100 -> "Title must be less than 100 characters"
            else -> null
        }
    }

    fun validateIngredients(value: String): String? {
        return when {
            value.isBlank() -> "Ingredients are required"
            value.split("\n")
                .filter { it.trim().isNotBlank() }.size < 2 -> "Add at least 2 ingredients"

            else -> null
        }
    }

    fun validateInstructions(value: String): String? {
        return when {
            value.isBlank() -> "Instructions are required"
            value.length < 10 -> "Instructions must be more detailed"
            else -> null
        }
    }

    val isFormValid = titleError == null && ingredientsError == null &&
            instructionsError == null && title.isNotBlank() &&
            ingredients.isNotBlank() && instructions.isNotBlank()

    error?.let { errorMessage ->
        LaunchedEffect(errorMessage) {
            Log.i("errormsg", errorMessage)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecipeTopAppBar(
                title = "Add Recipe",
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )

                    AnimatedVisibility(
                        visible = !isFormValid,
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Please fill all required fields",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (isFormValid) {
                                val recipe = Recipe(
                                    id = 0,
                                    title = title.trim(),
                                    ingredients = ingredients.split("\n")
                                        .map { it.trim() }
                                        .filter { it.isNotBlank() },
                                    instructions = instructions.trim(),
                                    cookingTime = cookingTime.trim().ifBlank { "Not Specified" },
                                    videoUrl = videoUrl.trim()
                                )
                                viewModel.addRecipe(recipe, onSaveClick)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        enabled = isFormValid && !isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        AnimatedContent(
                            targetState = isLoading,
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            },
                            label = "button_content"
                        ) { loading ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (loading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        strokeWidth = 2.dp,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Saving...",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Save,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Save Recipe",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                RecipeInputField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = validateTitle(it)
                    },
                    label = "Recipe Title",
                    placeholder = "Enter a delicious recipe name",
                    errorMessage = titleError,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }

            item {
                RecipeInputField(
                    value = cookingTime,
                    onValueChange = { cookingTime = it },
                    label = "Cooking Time",
                    placeholder = "e.g., 30 minutes, 2 hours",
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }

            item {
                RecipeInputField(
                    value = ingredients,
                    onValueChange = {
                        ingredients = it
                        ingredientsError = validateIngredients(it)
                    },
                    label = "Ingredients",
                    placeholder = "Enter each ingredient on a new line\ne.g., 2 cups flour\n1 tsp salt",
                    errorMessage = ingredientsError,
                    minLines = 4,
                    maxLines = 8,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }

            item {
                RecipeInputField(
                    value = instructions,
                    onValueChange = {
                        instructions = it
                        instructionsError = validateInstructions(it)
                    },
                    label = "Instructions",
                    placeholder = "Describe the cooking steps in detail...",
                    errorMessage = instructionsError,
                    minLines = 6,
                    maxLines = 12,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }

            item {
                RecipeInputField(
                    value = videoUrl,
                    onValueChange = { videoUrl = it },
                    label = "YouTube Video Link (Optional)",
                    placeholder = "Paste YouTube URL here",
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.VideoLibrary,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp)) // Bottom padding for FAB
            }
        }
    }
}

@Composable
private fun RecipeInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    errorMessage: String? = null,
    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.animateContentSize()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            },
            leadingIcon = leadingIcon,
            isError = errorMessage != null,
            singleLine = singleLine,
            minLines = if (singleLine) 1 else minLines,
            maxLines = if (singleLine) 1 else maxLines,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorLabelColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(12.dp)
        )

        AnimatedVisibility(
            visible = errorMessage != null,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            errorMessage?.let { error ->
                Row(
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}