package com.avirajsharma.recipeapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase: RoomDatabase(){
    abstract fun recipeDao(): RecipeDao

    companion object{
        @Volatile
        private var INSTANCE : RecipeDatabase? = null


        fun getDataBase(context: Context): RecipeDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, //It is passed to make sure Room uses app-level context (not just a screen).This prevents memory leaks — especially when DB is accessed outside activities.
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}