package com.avirajsharma.recipeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.domain.usecase.GetRecipeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
): ViewModel() {

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe : StateFlow<Recipe?> = _recipe.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val result = getRecipeByIdUseCase(recipeId)
                _recipe.value = result
                _isLoading.value = false
            } catch (exception: Exception) {
                _error.value = exception.message ?: "Failed to load recipe"
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

}