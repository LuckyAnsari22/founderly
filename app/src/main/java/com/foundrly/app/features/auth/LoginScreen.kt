package com.foundrly.app.features.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.foundrly.app.R
import com.foundrly.app.core.theme.WineAccent
import com.foundrly.app.core.theme.WineBackground
import com.foundrly.app.core.theme.WineError
import com.foundrly.app.core.theme.WineGlassBorder
import com.foundrly.app.core.theme.WineGlassLight
import com.foundrly.app.core.theme.WineOnSurfaceVariant
import com.foundrly.app.core.theme.WineOutline
import com.foundrly.app.core.theme.WinePrimary
import com.foundrly.app.core.theme.WineSurfaceVariant
import com.foundrly.app.core.theme.WineText

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    // Staggered entrance animations
    val heroAlpha  = remember { Animatable(0f) }
    val heroSlide  = remember { Animatable(32f) }
    val formAlpha  = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        heroAlpha.animateTo(1f, tween(600, easing = EaseOutCubic))
        heroSlide.animateTo(0f, tween(600, easing = EaseOutCubic))
        formAlpha.animateTo(1f, tween(500, delayMillis = 200))
    }

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) onLoginSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WineBackground)
    ) {
        // Subtle bottom-right accent glow
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.BottomEnd)
                .background(
                    Brush.radialGradient(
                        colors = listOf(WineAccent.copy(alpha = 0.12f), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            // Logo — small, top of page
            Box(modifier = Modifier.graphicsLayer { alpha = heroAlpha.value }) {
                Image(
                    painter = painterResource(R.drawable.foundrly_logo),
                    contentDescription = "Foundrly",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(48.dp).width(160.dp)
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

            // Editorial hero headline — left-aligned, massive, confident
            Box(
                modifier = Modifier.graphicsLayer {
                    alpha = heroAlpha.value
                    translationY = heroSlide.value
                }
            ) {
                Column {
                    Text(
                        text = "Welcome",
                        color = WineText,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1.5).sp,
                        lineHeight = 54.sp
                    )
                    Text(
                        text = "back,",
                        color = WineText,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1.5).sp,
                        lineHeight = 54.sp
                    )
                    Text(
                        text = "Founder.",
                        color = WineAccent,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1.5).sp,
                        lineHeight = 54.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Your startup journey continues.",
                        color = WineOnSurfaceVariant,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(52.dp))

            // Minimal editorial form
            Box(modifier = Modifier.graphicsLayer { alpha = formAlpha.value }) {
                Column {
                    // Input fields — bottom border only, no box
                    LineTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "EMAIL ADDRESS",
                        icon = Icons.Default.Email
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    LineTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "PASSWORD",
                        icon = Icons.Default.Lock,
                        isPassword = true
                    )

                    if (authState is AuthState.Error) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = WineError,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // Primary CTA — full-width, gradient
                    WineButton(
                        text = "Continue Building →",
                        onClick = { viewModel.login(email, password) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Google — minimal, secondary
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(WineGlassLight)
                            .border(1.dp, WineGlassBorder, RoundedCornerShape(12.dp))
                            .clickable { }
                            .padding(vertical = 16.dp),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/768px-Google_%22G%22_logo.svg.png",
                            contentDescription = "Google",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Continue with Google", color = WineText, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Sign-up link — very subtle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text("New here? ", color = WineOnSurfaceVariant, fontSize = 14.sp)
                        Text(
                            "Create your startup",
                            color = WineAccent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { }
                        )
                        Text(" →", color = WineAccent, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

// ── Bottom-border-only text field — editorial/luxury input style ──────────────
@Composable
fun LineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val lineColor by animateColorAsState(
        targetValue = if (isFocused) WineAccent else WineOutline,
        animationSpec = tween(250),
        label = "lineColor"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // ALL-CAPS label with wide tracking — premium editorial detail
        Text(
            text = label,
            color = if (isFocused) WineAccent else WineOnSurfaceVariant,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    // Only a bottom line — editorial style
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.2.dp.toPx()
                    )
                }
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = lineColor,
                modifier = Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                if (value.isEmpty()) {
                    Text("—", color = WineOutline.copy(alpha = 0.6f), fontSize = 15.sp)
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    interactionSource = interactionSource,
                    textStyle = TextStyle(color = WineText, fontSize = 15.sp),
                    visualTransformation = if (isPassword) PasswordVisualTransformation()
                    else VisualTransformation.None,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// ── Gradient CTA button ───────────────────────────────────────────────────────
@Composable
fun WineButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.horizontalGradient(listOf(WinePrimary, WineAccent)))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = WineText, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}

// Backward-compat aliases used elsewhere
@Composable
fun WineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false
) = LineTextField(value, onValueChange, label, icon, isPassword)

@Composable
fun FoundrlyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false
) = LineTextField(value, onValueChange, label, icon, isPassword)

@Composable
fun FoundrlyButton(text: String, onClick: () -> Unit) = WineButton(text, onClick)
