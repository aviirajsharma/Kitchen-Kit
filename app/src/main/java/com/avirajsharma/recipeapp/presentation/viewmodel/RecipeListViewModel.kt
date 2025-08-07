package com.avirajsharma.recipeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.domain.usecase.DeleteRecipeUseCase
import com.avirajsharma.recipeapp.domain.usecase.GetAllRecipesUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getAllRecipesUseCase: GetAllRecipesUsecase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
): ViewModel(){
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipe: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes(){
        viewModelScope.launch {
            _isLoading.value = true
            getAllRecipesUseCase()
                .catch { exception ->
                    _error.value = exception.message ?: "Unknown error occurred"
                    _isLoading.value = false
                }
                .collect { recipeList ->
                    _recipes.value = recipeList
                    _isLoading.value = false
                    _error.value = null
                }
        }
    }

    fun deleteRecipe(recipe: Recipe){
        viewModelScope.launch {
            try {
                deleteRecipeUseCase(recipe)
                _error.value = null
            } catch (exception: Exception) {
                _error.value = exception.message ?: "Failed to delete recipe"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}