package com.avirajsharma.recipeapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String,
    val ingredients : List<String>,
    val instructions : String,
    val cookingTime : String = "30 mins",
    val videoUrl : String
)
