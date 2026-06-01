package com.foundrly.app.features.dashboard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.foundrly.app.core.theme.WineAccent
import com.foundrly.app.core.theme.WineBackground
import com.foundrly.app.core.theme.WineError
import com.foundrly.app.core.theme.WineGlassBorder
import com.foundrly.app.core.theme.WineGlassLight
import com.foundrly.app.core.theme.WineOnSurfaceVariant
import com.foundrly.app.core.theme.WineOutline
import com.foundrly.app.core.theme.WineOutlineVariant
import com.foundrly.app.core.theme.WinePrimary
import com.foundrly.app.core.theme.WineSuccess
import com.foundrly.app.core.theme.WineSurfaceVariant
import com.foundrly.app.core.theme.WineText
import com.foundrly.app.core.theme.WineWarning
import com.foundrly.app.data.local.TaskEntity
import java.util.Calendar

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()) {
    val tasks by viewModel.tasks.collectAsState()

    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val timeLabel = when (hour) {
        in 0..11 -> "MORNING"
        in 12..16 -> "AFTERNOON"
        else -> "EVENING"
    }
    val dayLabel = "JUN 01"  // in production: derive from Calendar

    // Content slides in from bottom on launch
    val contentSlide = remember { Animatable(60f) }
    val contentAlpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        contentAlpha.animateTo(1f, tween(500, easing = EaseOutCubic))
        contentSlide.animateTo(0f, tween(500, easing = EaseOutCubic))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WineBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = contentAlpha.value
                    translationY = contentSlide.value
                },
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // ── HERO ──────────────────────────────────────────────────────────
            item { HeroSection(timeLabel, dayLabel) }

            item {
                // Thin horizontal rule — editorial divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(WineOutline)
                )
            }

            item { Spacer(modifier = Modifier.height(28.dp)) }

            // ── BENTO GRID ────────────────────────────────────────────────────
            item { BentoGrid() }

            item { Spacer(modifier = Modifier.height(28.dp)) }

            // ── AI BRIEFING ───────────────────────────────────────────────────
            item { AiBriefingCard() }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            // ── ON DECK (TASKS) ───────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "ON DECK",
                            color = WineAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 3.sp
                        )
                        Text(
                            text = "Today's Focus",
                            color = WineText,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${tasks.count { it.isCompleted }} / ${tasks.size} done",
                        color = WineOnSurfaceVariant,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Editorial numbered task items with hairline dividers
            items(tasks, key = { it.id }) { task ->
                val index = tasks.indexOf(task) + 1
                EditorialTaskItem(
                    task = task,
                    number = index,
                    onToggle = { viewModel.toggleTask(task) },
                    onDelete = { viewModel.deleteTask(task) }
                )
            }

            item { Spacer(modifier = Modifier.height(28.dp)) }

            // ── UPCOMING EVENT ────────────────────────────────────────────────
            item { UpcomingEventCard() }
        }
    }
}

// ── HERO SECTION: pure typography — no stock photo ───────────────────────────
@Composable
fun HeroSection(timeLabel: String, dayLabel: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(WineBackground)
    ) {
        // Enormous "F" brand watermark — barely visible, top-right
        Text(
            text = "F",
            fontSize = 300.sp,
            fontWeight = FontWeight.Black,
            color = WinePrimary.copy(alpha = 0.045f),
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier.padding(
                start = 24.dp, end = 24.dp,
                top = 56.dp, bottom = 28.dp
            )
        ) {
            // Top bar: avatar row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(WinePrimary, WineAccent))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        color = WineText,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Aryan Mehta",
                        color = WineText,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "EcoTech · Founder",
                        color = WineOnSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                BadgedBox(
                    badge = {
                        Badge(containerColor = WineAccent) {
                            Text("3", color = WineText, fontSize = 9.sp)
                        }
                    }
                ) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = WineText,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(44.dp))

            // Overline — date + period
            Text(
                text = "$dayLabel  ·  GOOD $timeLabel",
                color = WineAccent,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Massive editorial headline
            Text(
                text = "Founder\nDashboard.",
                color = WineText,
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1.5).sp,
                lineHeight = 60.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Status pills — horizontal, compact
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusPill(text = "🔥  Day 47", color = WineWarning)
                StatusPill(text = "⚡  MVP Stage", color = WineAccent)
                StatusPill(text = "72% Health", color = WineSuccess)
            }
        }
    }
}

@Composable
fun StatusPill(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.10f), RoundedCornerShape(8.dp))
            .border(1.dp, color.copy(alpha = 0.30f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ── BENTO GRID: asymmetric card layout ───────────────────────────────────────
@Composable
fun BentoGrid() {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Wide card: Startup with progress
        StartupBentoCard()

        // Three compact stat tiles
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MiniStatTile(
                value = "72%",
                label = "HEALTH",
                color = WineSuccess,
                modifier = Modifier.weight(1f)
            )
            MiniStatTile(
                value = "47",
                label = "DAY STREAK",
                color = WineWarning,
                modifier = Modifier.weight(1f)
            )
            MiniStatTile(
                value = "3",
                label = "TEAM",
                color = WineAccent,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StartupBentoCard() {
    var mounted = remember { false }
    val progress = remember { Animatable(0f) }
    val progressInt = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        if (!mounted) {
            mounted = true
            progress.animateTo(0.72f, tween(900, easing = EaseOutCubic))
            progressInt.animateTo(72f, tween(900, easing = EaseOutCubic))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(WineSurfaceVariant)
            .border(1.dp, WineOutline, RoundedCornerShape(18.dp))
    ) {
        // Subtle left accent strip
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(80.dp)
                .align(Alignment.CenterStart)
                .background(
                    Brush.verticalGradient(listOf(WinePrimary, WineAccent)),
                    RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)
                )
        )
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Startup name + logo monogram
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Brush.linearGradient(listOf(WinePrimary, WineAccent))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ET",
                            color = WineText,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "EcoTech",
                            color = WineText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "MVP STAGE",
                            color = WineOnSurfaceVariant,
                            fontSize = 9.sp,
                            letterSpacing = 2.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "STARTUP HEALTH",
                    color = WineOnSurfaceVariant,
                    fontSize = 9.sp,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { progress.value },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(CircleShape),
                    color = WineAccent,
                    trackColor = WineOutline
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "On Track · Day 47 of build",
                    color = WineOnSurfaceVariant,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Circle percent display — the key visual focus of this card
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(WineAccent.copy(alpha = 0.07f))
                    .border(1.5.dp, WineAccent.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${progressInt.value.toInt()}",
                        color = WineAccent,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 26.sp
                    )
                    Text(
                        text = "%",
                        color = WineOnSurfaceVariant,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MiniStatTile(value: String, label: String, color: Color, modifier: Modifier) {
    Column(
        modifier = modifier
            .background(WineSurfaceVariant, RoundedCornerShape(14.dp))
            .border(1.dp, WineOutline, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = value,
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 24.sp
        )
        Text(
            text = label,
            color = WineOnSurfaceVariant,
            fontSize = 9.sp,
            letterSpacing = 1.5.sp
        )
    }
}

// ── AI BRIEFING: the one editorial accent card ────────────────────────────────
@Composable
fun AiBriefingCard() {
    // Pulse the accent border
    val infiniteTransition = rememberInfiniteTransition(label = "aiBriefing")
    val borderAlpha by infiniteTransition.animateFloat(
        initialValue = 0.20f,
        targetValue = 0.55f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse),
        label = "borderAlpha"
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(WinePrimary.copy(alpha = 0.22f), WineAccent.copy(alpha = 0.08f))
                )
            )
            .border(1.dp, WineAccent.copy(alpha = borderAlpha), RoundedCornerShape(18.dp))
            .padding(22.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("✦", color = WineAccent, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "AI BRIEFING",
                    color = WineAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.5.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "\"Complete your Lean Canvas today — it's the highest-impact step before your seed pitch.\"",
                color = WineText,
                fontSize = 16.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Hairline divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(WineOutline)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Open in AI Chat",
                    color = WineAccent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Outlined.ArrowForward,
                    contentDescription = null,
                    tint = WineAccent,
                    modifier = Modifier.size(13.dp)
                )
            }
        }
    }
}

// ── EDITORIAL NUMBERED TASK ITEM ──────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorialTaskItem(
    task: TaskEntity,
    number: Int,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
        LaunchedEffect(Unit) { onDelete() }
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(0.dp))
                    .background(
                        Brush.horizontalGradient(listOf(WinePrimary, WineError))
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    "Delete",
                    color = WineText,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 24.dp)
                )
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Editorial number — wide-tracked, very muted
                Text(
                    text = number.toString().padStart(2, '0'),
                    color = WineOutline,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.width(30.dp)
                )

                Spacer(modifier = Modifier.width(14.dp))

                // Task title
                Text(
                    text = task.title,
                    color = if (task.isCompleted) WineOnSurfaceVariant else WineText,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough
                    else TextDecoration.None,
                    fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.Medium,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(14.dp))

                // Minimal circle checkbox
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(
                            if (task.isCompleted) WineAccent else Color.Transparent
                        )
                        .border(
                            1.5.dp,
                            if (task.isCompleted) WineAccent else WineOutlineVariant,
                            CircleShape
                        )
                        .clickable { onToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    if (task.isCompleted) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = null,
                            tint = WineText,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }

            // Hairline divider between tasks
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(1.dp)
                    .background(WineOutline)
            )
        }
    }
}

// ── UPCOMING EVENT ────────────────────────────────────────────────────────────
@Composable
fun UpcomingEventCard() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text(
            text = "UPCOMING",
            color = WineOnSurfaceVariant,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WineSurfaceVariant, RoundedCornerShape(16.dp))
                .border(1.dp, WineOutline, RoundedCornerShape(16.dp))
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date block
            Column(
                modifier = Modifier
                    .size(52.dp, 56.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(WineAccent.copy(alpha = 0.10f))
                    .border(1.dp, WineGlassBorder, RoundedCornerShape(10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "JUN",
                    color = WineAccent,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "15",
                    color = WineAccent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Startup Bootcamp 2026",
                    color = WineText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "9 AM · IIT Delhi · Virtual",
                    color = WineOnSurfaceVariant,
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(WineAccent.copy(alpha = 0.12f))
                    .border(1.dp, WineAccent.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .clickable { }
                    .padding(horizontal = 12.dp, vertical = 7.dp)
            ) {
                Text(
                    text = "RSVP",
                    color = WineAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

// Backward-compat aliases for callers of old function names
@Composable
fun StatCard(value: String, label: String, accentColor: Color, modifier: Modifier = Modifier) =
    MiniStatTile(value, label, accentColor, modifier)

@Composable
fun WineStatCard(value: String, label: String, accentColor: Color, modifier: Modifier = Modifier) =
    MiniStatTile(value, label, accentColor, modifier)
