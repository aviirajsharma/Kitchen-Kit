package com.avirajsharma.recipeapp.domain.model

data class Recipe(
    val id: Int = 0,
    val title: String,
    val ingredients: List<String>,
    val instructions: String,
    val cookingTime: String = "30 mins",
    val videoUrl: String
)
