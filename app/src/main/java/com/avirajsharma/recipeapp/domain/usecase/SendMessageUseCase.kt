package com.avirajsharma.recipeapp.domain.usecase

import com.avirajsharma.recipeapp.domain.model.Message
import com.avirajsharma.recipeapp.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private  val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        message: String,
        conversationHistory: List<Message>
    ): Result<String> {
        return chatRepository.sendMessage(message, conversationHistory)
    }
}