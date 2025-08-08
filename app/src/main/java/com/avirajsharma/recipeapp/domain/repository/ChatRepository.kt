package com.avirajsharma.recipeapp.domain.repository

import com.avirajsharma.recipeapp.domain.model.Message

interface ChatRepository {
    suspend fun sendMessage(
        message: String,
        conversationHistory: List<Message>
    ): Result<String>
}