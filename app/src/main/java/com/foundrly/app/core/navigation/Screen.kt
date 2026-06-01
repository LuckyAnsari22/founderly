package com.foundrly.app.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main") // Shell for bottom nav
    object Dashboard : Screen("dashboard")
    object Community : Screen("community")
    object AiChat : Screen("aichat")
    object Profile : Screen("profile")
}
