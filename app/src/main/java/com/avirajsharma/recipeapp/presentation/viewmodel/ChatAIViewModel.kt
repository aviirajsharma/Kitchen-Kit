package com.avirajsharma.recipeapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avirajsharma.recipeapp.domain.model.Message
import com.avirajsharma.recipeapp.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatAIViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    val messageList = mutableStateListOf<Message>()

    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                // Add user message
                val userMessage = Message(
                    id = UUID.randomUUID().toString(),
                    content = question,
                    role = "user"
                )
                messageList.add(userMessage)

                // Add loading message
                val loadingMessage = Message(
                    id = UUID.randomUUID().toString(),
                    content = "Typing...",
                    role = "model"
                )
                messageList.add(loadingMessage)

                // Send message and get response
                val result = sendMessageUseCase(question, messageList.filter { it.content != "Typing..." })

                // Remove loading message
                messageList.removeLastOrNull()

                if (result.isSuccess) {
                    // Add model response
                    val modelMessage = Message(
                        id = UUID.randomUUID().toString(),
                        content = result.getOrNull() ?: "No response",
                        role = "model"
                    )
                    messageList.add(modelMessage)
                } else {
                    // Add error message
                    val errorMessage = Message(
                        id = UUID.randomUUID().toString(),
                        content = "Error: ${result.exceptionOrNull()?.message}",
                        role = "model"
                    )
                    messageList.add(errorMessage)
                }
            } catch (e: Exception) {
                // Remove loading message and add error
                messageList.removeLastOrNull()
                val errorMessage = Message(
                    id = UUID.randomUUID().toString(),
                    content = "Error: ${e.message}",
                    role = "model"
                )
                messageList.add(errorMessage)
            }
        }
    }

    fun clearChat() {
        messageList.clear()
    }
}