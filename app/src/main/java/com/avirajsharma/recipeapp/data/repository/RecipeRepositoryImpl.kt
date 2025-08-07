package com.avirajsharma.recipeapp.data.repository

import com.avirajsharma.recipeapp.data.local.database.RecipeDao
import com.avirajsharma.recipeapp.data.local.entities.toDomainModel
import com.avirajsharma.recipeapp.data.local.entities.toEntity
import com.avirajsharma.recipeapp.domain.model.Recipe
import com.avirajsharma.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getRecipeById(id: Int): Recipe? {
        return recipeDao.getRecipeById(id = id)?.toDomainModel()
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        return recipeDao.insertRecipe(recipe = recipe.toEntity())
    }

    override suspend fun updateRecipe(recipe: Recipe) {
        return recipeDao.updateRecipe(recipe = recipe.toEntity())
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        return recipeDao.deleteRecipe(recipe = recipe.toEntity())
    }

    override suspend fun deleteAllRecipe() {
        return recipeDao.deleteAllRecipes()
    }

}