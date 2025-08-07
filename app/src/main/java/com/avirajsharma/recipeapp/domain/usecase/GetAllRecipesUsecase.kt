package com.avirajsharma.recipeapp.domain.usecase

import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRecipesUsecase @Inject constructor(
    private val repository: RecipeRepository
){
    operator fun invoke() : Flow<List<Recipe>>{
        return repository.getAllRecipes()
    }
}