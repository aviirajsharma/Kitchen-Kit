package com.avirajsharma.recipeapp.di

import com.avirajsharma.recipeapp.data.repository.ChatRepositoryImpl
import com.avirajsharma.recipeapp.domain.repository.ChatRepository
import com.avirajsharma.recipeapp.utils.Constants
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = Constants.API_KEY
        )
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        generativeModel: GenerativeModel
    ): ChatRepository {
        return ChatRepositoryImpl(generativeModel)
    }
}