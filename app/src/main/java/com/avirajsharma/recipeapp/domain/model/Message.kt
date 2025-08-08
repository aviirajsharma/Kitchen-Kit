package com.avirajsharma.recipeapp.domain.model

data class Message(
    val id: String = "",
    val content: String,
    val role: String, // "user" or "model"
    val timestamp: Long = System.currentTimeMillis()
)
