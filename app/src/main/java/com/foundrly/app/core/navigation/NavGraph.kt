package com.foundrly.app.core.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.foundrly.app.core.theme.WineAccent
import com.foundrly.app.core.theme.WineBackground
import com.foundrly.app.core.theme.WineOnSurfaceVariant
import com.foundrly.app.core.theme.WineOutline
import com.foundrly.app.core.theme.WineSurfaceVariant
import com.foundrly.app.features.ai_chat.ChatScreen
import com.foundrly.app.features.auth.LoginScreen
import com.foundrly.app.features.community.CommunityScreen
import com.foundrly.app.features.dashboard.DashboardScreen
import com.foundrly.app.features.profile.ProfileScreen
import com.foundrly.app.features.splash.SplashScreen

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToLogin = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val bottomNavController = rememberNavController()

    val navItems = listOf(
        BottomNavItem(
            Screen.Dashboard.route,
            "Dashboard",
            Icons.Outlined.Dashboard,
            Icons.Filled.Dashboard
        ),
        BottomNavItem(
            Screen.Community.route,
            "Community",
            Icons.Outlined.Groups,
            Icons.Outlined.Groups
        ),
        BottomNavItem(
            Screen.AiChat.route,
            "AI",
            Icons.Outlined.AutoAwesome,
            Icons.Outlined.AutoAwesome
        ),
        BottomNavItem(
            Screen.Profile.route,
            "Profile",
            Icons.Outlined.Person,
            Icons.Filled.Person
        )
    )

    Scaffold(
        containerColor = WineBackground,
        bottomBar = {
            val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            NavigationBar(
                containerColor = WineSurfaceVariant,
                contentColor = WineAccent,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .height(72.dp)
                    .border(width = 1.dp, color = WineOutline)
            ) {
                navItems.forEach { item ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == item.route } == true
                    val iconColor by animateColorAsState(
                        targetValue = if (isSelected) WineAccent else WineOnSurfaceVariant,
                        animationSpec = tween(200),
                        label = "iconColor_${item.route}"
                    )
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Box(contentAlignment = Alignment.Center) {
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(WineAccent.copy(alpha = 0.18f))
                                    )
                                }
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon
                                    else item.icon,
                                    contentDescription = item.label,
                                    tint = iconColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 11.sp,
                                color = iconColor
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = WineAccent,
                            selectedTextColor = WineAccent,
                            indicatorColor = Color.Transparent,
                            unselectedIconColor = WineOnSurfaceVariant,
                            unselectedTextColor = WineOnSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen() }
            composable(Screen.Community.route) { CommunityScreen() }
            composable(Screen.AiChat.route) { ChatScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}
