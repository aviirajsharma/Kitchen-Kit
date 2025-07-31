package com.avirajsharma.recipeapp

import androidx.compose.runtime.mutableStateListOf
import com.avirajsharma.recipeapp.database.Recipe
import com.avirajsharma.recipeapp.database.RecipeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeRepository(private val recipeDao: RecipeDao) {
    private val _recipes = mutableStateListOf<Recipe>()
    val recipes: List<Recipe> = _recipes

    init {
        // Observe Database changes
        CoroutineScope(Dispatchers.IO).launch{
            recipeDao.getAllRecipes().collect{ recipeList->
                _recipes.clear()
                _recipes.addAll(recipeList)
            }
        }
    }

    fun getRecipeById(id: Int): Recipe? {
        return _recipes.find { it.id == id }
    }

    fun addRecipe(recipe: Recipe) {
        CoroutineScope(Dispatchers.IO).launch {
            recipeDao.insertRecipe(recipe)
        }
    }

    companion object{
        private var INSTANCE : RecipeRepository? = null

        fun getInstance(recipeDao: RecipeDao): RecipeRepository{
            return INSTANCE ?: synchronized(this){
                val instance = RecipeRepository(recipeDao)
                INSTANCE = instance
                instance
            }
        }
    }
}