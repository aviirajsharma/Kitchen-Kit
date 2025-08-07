package com.avirajsharma.recipeapp.domain.usecase

import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeByIdUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id:Int): Recipe? {
        return repository.getRecipeById(id)
    }
}