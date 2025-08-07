package com.avirajsharma.recipeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.domain.usecase.AddRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    fun addRecipe(recipe: Recipe, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                addRecipeUseCase(recipe)
                _isLoading.value = false
                _isSuccess.value = true
                onSuccess()
            } catch (exception: Exception) {
                _error.value = exception.message ?: "Failed to add recipe"
                _isLoading.value = false
                _isSuccess.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun resetSuccess() {
        _isSuccess.value = false
    }
}