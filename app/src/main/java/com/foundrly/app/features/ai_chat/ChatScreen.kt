package com.foundrly.app.features.ai_chat

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.foundrly.app.core.theme.WineAccent
import com.foundrly.app.core.theme.WineBackground
import com.foundrly.app.core.theme.WineOnSurfaceVariant
import com.foundrly.app.core.theme.WineOutline
import com.foundrly.app.core.theme.WinePrimary
import com.foundrly.app.core.theme.WineSurfaceVariant
import com.foundrly.app.core.theme.WineText
import com.foundrly.app.data.model.Message

@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel()) {
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()
    var inputText by remember { mutableStateOf("") }
    
    val contentAlpha = remember { Animatable(0f) }
    val contentSlide = remember { Animatable(60f) }

    LaunchedEffect(Unit) {
        contentAlpha.animateTo(1f, tween(500, easing = EaseOutCubic))
        contentSlide.animateTo(0f, tween(500, easing = EaseOutCubic))
    }

    Scaffold(
        containerColor = WineBackground,
        modifier = Modifier.graphicsLayer {
            alpha = contentAlpha.value
            translationY = contentSlide.value
        },
        topBar = {
            EditorialChatHeader()
        },
        bottomBar = {
            EditorialChatInput(
                text = inputText,
                onTextChange = { inputText = it },
                onSend = {
                    viewModel.sendMessage(inputText)
                    inputText = ""
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(WineBackground)
        ) {
            if (messages.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    EditorialSuggestedPrompts { viewModel.sendMessage(it) }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    items(messages) { message ->
                        EditorialMessage(message)
                    }
                    if (isTyping) {
                        item { EditorialTypingIndicator() }
                    }
                }
            }
        }
    }
}

@Composable
fun EditorialChatHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WineBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "AI CO-FOUNDER",
                    color = WineAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 3.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Foundrly AI",
                        color = WineText,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    // Pulsing online dot
                    val statusTransition = rememberInfiniteTransition(label = "statusPulse")
                    val dotAlpha by statusTransition.animateFloat(
                        initialValue = 0.4f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse),
                        label = "dotAlpha"
                    )
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(WineAccent.copy(alpha = dotAlpha), CircleShape)
                    )
                }
            }
            Icon(
                Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = WineText,
                modifier = Modifier.size(24.dp)
            )
        }
        // Hairline divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(WineOutline)
        )
    }
}

@Composable
fun EditorialSuggestedPrompts(onPromptClick: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = "Hello, Founder.",
            color = WineText,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = (-1).sp,
            lineHeight = 44.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "I'm ready to help you validate, build, and scale your startup.",
            color = WineOnSurfaceVariant,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "SUGGESTED ACTIONS",
            color = WineOutline,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        val prompts = listOf(
            "Validate my startup idea",
            "Build a pitch deck",
            "Find co-founders",
            "Write a lean canvas"
        )
        
        prompts.forEach { prompt ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPromptClick(prompt) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prompt,
                    color = WineText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = WineAccent,
                    modifier = Modifier.size(16.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(WineOutline)
            )
        }
    }
}

@Composable
fun EditorialMessage(message: Message) {
    if (message.isUser) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 280.dp)
                        .background(WineSurfaceVariant, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = message.content,
                        color = WineText,
                        fontSize = 15.sp,
                        lineHeight = 24.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "YOU",
                    color = WineOnSurfaceVariant,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // Left accent line instead of a bubble for the AI
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .padding(vertical = 4.dp) // align with text
                    .background(Brush.verticalGradient(listOf(WinePrimary, WineAccent)))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "FOUNDRLY AI",
                    color = WineAccent,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = message.content,
                    color = WineText,
                    fontSize = 15.sp,
                    lineHeight = 26.sp,
                    modifier = Modifier.widthIn(max = 280.dp)
                )
            }
        }
    }
}

@Composable
fun EditorialTypingIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(24.dp)
                .background(Brush.verticalGradient(listOf(WinePrimary, WineAccent)))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.height(24.dp)
        ) {
            val infiniteTransition = rememberInfiniteTransition(label = "typing")
            repeat(3) { index ->
                val dotOffset by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = -6f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            400,
                            delayMillis = index * 150,
                            easing = FastOutLinearInEasing
                        ),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "dotOffset$index"
                )
                Box(
                    modifier = Modifier
                        .offset(y = dotOffset.dp)
                        .size(6.dp)
                        .background(WineAccent, CircleShape)
                )
            }
        }
    }
}

@Composable
fun EditorialChatInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WineBackground)
    ) {
        // Hairline top border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(WineOutline)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Mic,
                contentDescription = "Mic",
                tint = WineOnSurfaceVariant,
                modifier = Modifier.size(22.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))

            // Text Field with bottom line only
            Box(
                modifier = Modifier
                    .weight(1f)
                    .drawBehind {
                        drawLine(
                            color = WineOutline,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.isEmpty()) {
                    Text(
                        "Ask your AI co-founder...",
                        color = WineOutline,
                        fontSize = 15.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
                BasicTextField(
                    value = text,
                    onValueChange = onTextChange,
                    textStyle = TextStyle(color = WineText, fontSize = 15.sp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))

            val canSend = text.isNotBlank()
            Text(
                text = "SEND",
                color = if (canSend) WineAccent else WineOutline,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.clickable(enabled = canSend) { onSend() }
            )
        }
    }
}
