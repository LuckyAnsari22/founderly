package com.foundrly.app.data.model

data class Post(
    val id: Int,
    val avatarUrl: String,
    val startupName: String,
    val stage: String,
    val content: String,
    val likes: Int,
    val comments: Int,
    val timestamp: String,
    val isLiked: Boolean = false
)
