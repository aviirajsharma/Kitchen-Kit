package com.avirajsharma.recipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avirajsharma.recipeapp.data.local.database.Converters
import com.avirajsharma.recipeapp.domain.model.Recipe

@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String,
    val ingredients : List<String>,
    val instructions : String,
    val cookingTime : String = "30 mins",
    val videoUrl : String
)

//RecipeEntity to RecipeDomainModel

fun RecipeEntity.toDomainModel(): Recipe{
    return Recipe(
        id = id,
        title = title,
        ingredients = ingredients,
        instructions = instructions,
        cookingTime = cookingTime,
        videoUrl = videoUrl
    )
}

//Recipe to RecipeEntity

fun Recipe.toEntity(): RecipeEntity{
    return RecipeEntity(
        id = id,
        title = title,
        ingredients = ingredients,
        instructions = instructions,
        cookingTime = cookingTime,
        videoUrl = videoUrl
    )
}
