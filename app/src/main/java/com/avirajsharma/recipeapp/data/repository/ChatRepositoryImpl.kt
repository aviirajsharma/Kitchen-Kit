package com.avirajsharma.recipeapp.data.repository

import com.avirajsharma.recipeapp.domain.model.Message
import com.avirajsharma.recipeapp.domain.repository.ChatRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private  val generativeModel: GenerativeModel
): ChatRepository {
    override suspend fun sendMessage(
        message: String,
        conversationHistory: List<Message>
    ): Result<String> {
        return try {
            val chat = generativeModel.startChat(
                history = conversationHistory.map {
                    content(it.role) {
                        text(it.content)
                    }
                }.toList()
            )

            val response = chat.sendMessage(message)
            Result.success(response.text ?: "No response")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}