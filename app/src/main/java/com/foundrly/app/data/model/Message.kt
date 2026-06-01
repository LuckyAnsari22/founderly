package com.foundrly.app.data.model

data class Message(
    val id: Int,
    val content: String,
    val isUser: Boolean,
    val timestamp: String
)
