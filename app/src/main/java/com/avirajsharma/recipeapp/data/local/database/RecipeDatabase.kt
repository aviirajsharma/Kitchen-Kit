package com.avirajsharma.recipeapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.avirajsharma.recipeapp.data.local.entities.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase: RoomDatabase(){
    abstract fun recipeDao(): RecipeDao
}