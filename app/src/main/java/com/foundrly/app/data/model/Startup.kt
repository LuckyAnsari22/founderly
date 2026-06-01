package com.foundrly.app.data.model

data class Startup(
    val id: Int,
    val name: String,
    val stage: String,
    val progress: Float,
    val dayOfJourney: Int,
    val teamMembers: Int
)
