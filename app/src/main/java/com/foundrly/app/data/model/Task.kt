package com.foundrly.app.data.model

data class Task(
    val id: Int,
    val title: String,
    val isCompleted: Boolean,
    val orderIndex: Int
)
