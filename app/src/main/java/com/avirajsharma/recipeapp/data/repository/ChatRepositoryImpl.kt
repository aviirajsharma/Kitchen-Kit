package com.avirajsharma.recipeapp.data.repository

import com.avirajsharma.recipeapp.domain.model.Message
import com.avirajsharma.recipeapp.domain.repository.ChatRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel
) : ChatRepository {
    private val systemPrompt = """
    You are Chef-MateAI, an expert Indian chef.
    Always:
    1. Answer only cooking/recipe questions (vegetarian or non-vegetarian).
    2. Give every response an Indian touch (spices, regional styles, serving ideas).
    3. Be accurate, concise, and practical.
    4. For recipes, always include: cooking time, ingredients list, and step-by-step instructions.
    5. Suggest healthy or regional variations when possible.
    If asked about non-food topics, say:
    "I'm Chef-MateAI, and I specialize in cooking with an Indian touch!"
    """.trimIndent()

    override suspend fun sendMessage(
        message: String,
        conversationHistory: List<Message>
    ): Result<String> {
        return try {
            val fullHistory = if (conversationHistory.isEmpty()) {
                listOf(
                    content("user") { text(systemPrompt) },
                    content("model") { text("Hello! I'm Chef-MateAI, your personal culinary assistant! I'm here to help you with recipes, cooking techniques, ingredient questions, meal planning, and everything food-related. What delicious creation can I help you with today? 👨‍🍳") }
                )
            } else {
                listOf(
                    content("user") { text(systemPrompt) },
                    content("model") { text("Understood! I'll focus on culinary assistance.") }
                ) + conversationHistory.map {
                    content(it.role) {
                        text(it.content)
                    }
                }
            }

            val chat = generativeModel.startChat(history = fullHistory)
            val response = chat.sendMessage(message)
            Result.success(
                response.text
                    ?: "I apologize, but I couldn't generate a response. Please try asking about a recipe or cooking technique!"
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}