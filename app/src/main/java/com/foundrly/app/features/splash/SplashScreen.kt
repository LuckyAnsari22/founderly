package com.foundrly.app.features.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foundrly.app.R
import com.foundrly.app.core.theme.WineAccent
import com.foundrly.app.core.theme.WineBackground
import com.foundrly.app.core.theme.WineOnSurfaceVariant
import com.foundrly.app.core.theme.WinePrimary
import com.foundrly.app.core.theme.WineText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    // Choreographed reveals
    val logoAlpha  = remember { Animatable(0f) }
    
    // Sequential word animations for "Build. Learn. Launch."
    val word1Alpha = remember { Animatable(0f) }
    val word1Slide = remember { Animatable(30f) }
    
    val word2Alpha = remember { Animatable(0f) }
    val word2Slide = remember { Animatable(30f) }
    
    val word3Alpha = remember { Animatable(0f) }
    val word3Slide = remember { Animatable(30f) }

    val tagAlpha   = remember { Animatable(0f) }
    val tagSlide   = remember { Animatable(10f) }

    LaunchedEffect(Unit) {
        // Logo fades in first
        launch { logoAlpha.animateTo(1f, tween(800, easing = EaseOutCubic)) }
        delay(300)
        
        // Build.
        launch { word1Alpha.animateTo(1f, tween(600, easing = EaseOutCubic)) }
        launch { word1Slide.animateTo(0f, tween(600, easing = EaseOutCubic)) }
        delay(250)
        
        // Learn.
        launch { word2Alpha.animateTo(1f, tween(600, easing = EaseOutCubic)) }
        launch { word2Slide.animateTo(0f, tween(600, easing = EaseOutCubic)) }
        delay(250)
        
        // Launch.
        launch { word3Alpha.animateTo(1f, tween(600, easing = EaseOutCubic)) }
        launch { word3Slide.animateTo(0f, tween(600, easing = EaseOutCubic)) }
        delay(300)
        
        // Tag line drifts in
        launch { tagAlpha.animateTo(1f, tween(700, easing = EaseOutCubic)) }
        launch { tagSlide.animateTo(0f, tween(700, easing = EaseOutCubic)) }
        
        delay(1300) // Pause for reading
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WineBackground)
    ) {
        // Full-screen radial spotlight
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            WinePrimary.copy(alpha = 0.28f),
                            WineBackground
                        ),
                        radius = 900f
                    )
                )
        )

        // Decorative brand letter
        Text(
            text = "F",
            fontSize = 380.sp,
            fontWeight = FontWeight.Black,
            color = WinePrimary.copy(alpha = 0.045f),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 0.dp, top = 0.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 36.dp),
        ) {
            Box(
                modifier = Modifier.graphicsLayer { alpha = logoAlpha.value }
            ) {
                Image(
                    painter = painterResource(R.drawable.foundrly_logo),
                    contentDescription = "Foundrly",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(72.dp)
                        .width(220.dp)
                )
            }

            Spacer(modifier = Modifier.height(56.dp))

            Column {
                // Word 1: Build.
                Text(
                    text = "Build.",
                    color = WineText,
                    fontSize = 68.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-2).sp,
                    lineHeight = 70.sp,
                    modifier = Modifier.graphicsLayer {
                        alpha = word1Alpha.value
                        translationY = word1Slide.value
                    }
                )
                // Word 2: Learn.
                Text(
                    text = "Learn.",
                    color = WineText,
                    fontSize = 68.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-2).sp,
                    lineHeight = 70.sp,
                    modifier = Modifier.graphicsLayer {
                        alpha = word2Alpha.value
                        translationY = word2Slide.value
                    }
                )
                // Word 3: Launch.
                Text(
                    text = "Launch.",
                    color = WineAccent,
                    fontSize = 68.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-2).sp,
                    lineHeight = 70.sp,
                    modifier = Modifier.graphicsLayer {
                        alpha = word3Alpha.value
                        translationY = word3Slide.value
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Thin accent rule — editorial divider
            Box(
                modifier = Modifier
                    .graphicsLayer { 
                        alpha = tagAlpha.value
                        translationY = tagSlide.value
                    }
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(WinePrimary, WineAccent)
                                )
                            )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "The founder operating system.",
                        color = WineOnSurfaceVariant,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.sp
                    )
                }
            }
        }

        Text(
            text = "v1.0",
            color = WineOnSurfaceVariant.copy(alpha = 0.4f),
            fontSize = 11.sp,
            letterSpacing = 1.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(36.dp)
        )
    }
}
