package com.avirajsharma.recipeapp.di

import android.content.Context
import androidx.room.Room
import com.avirajsharma.recipeapp.data.local.database.RecipeDao
import com.avirajsharma.recipeapp.data.local.database.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao{
        return database.recipeDao()
    }
}