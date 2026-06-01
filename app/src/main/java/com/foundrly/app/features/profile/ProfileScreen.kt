package com.foundrly.app.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.foundrly.app.core.theme.WineAccent
import com.foundrly.app.core.theme.WineBackground
import com.foundrly.app.core.theme.WineGlassBorder
import com.foundrly.app.core.theme.WineOnSurfaceVariant
import com.foundrly.app.core.theme.WineOutline
import com.foundrly.app.core.theme.WinePrimary
import com.foundrly.app.core.theme.WineSurfaceVariant
import com.foundrly.app.core.theme.WineText
import com.foundrly.app.core.theme.WineWarning

data class Achievement(val icon: ImageVector, val label: String, val color: Color)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen() {
    val contentAlpha = remember { Animatable(0f) }
    val contentSlide = remember { Animatable(60f) }

    LaunchedEffect(Unit) {
        contentAlpha.animateTo(1f, tween(500, easing = EaseOutCubic))
        contentSlide.animateTo(0f, tween(500, easing = EaseOutCubic))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WineBackground)
            .graphicsLayer {
                alpha = contentAlpha.value
                translationY = contentSlide.value
            },
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // ── TOP NAV ────────────────────────────────────────────────────────
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = WineText,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )
            }
        }

        // ── HERO ───────────────────────────────────────────────────────────
        item { EditorialProfileHero() }

        item { Spacer(modifier = Modifier.height(40.dp)) }

        // ── STATS (Flat / Minimal) ─────────────────────────────────────────
        item { EditorialStatsRow() }

        item { Spacer(modifier = Modifier.height(40.dp)) }

        item { DividerHairline() }

        // ── SKILLS ─────────────────────────────────────────────────────────
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item {
            SectionHeader("CORE COMPETENCIES")
            Spacer(modifier = Modifier.height(16.dp))
            EditorialSkills()
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
        item { DividerHairline() }
        item { Spacer(modifier = Modifier.height(32.dp)) }

        // ── ACHIEVEMENTS ───────────────────────────────────────────────────
        item {
            SectionHeader("MILESTONES")
            Spacer(modifier = Modifier.height(16.dp))
            EditorialAchievements()
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
        item { DividerHairline() }
        item { Spacer(modifier = Modifier.height(32.dp)) }

        // ── STARTUPS ───────────────────────────────────────────────────────
        item {
            SectionHeader("PORTFOLIO")
            Spacer(modifier = Modifier.height(16.dp))
            EditorialStartups()
        }

        item { Spacer(modifier = Modifier.height(48.dp)) }

        // ── EDIT CTA ───────────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(54.dp)
                    .border(1.dp, WineOutline, RoundedCornerShape(12.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "EDIT PROFILE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    color = WineText
                )
            }
        }
    }
}

@Composable
fun EditorialProfileHero() {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        // Massive Avatar
        Box {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=400&auto=format&fit=crop",
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
            )
            // Tiny status dot
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2DD4BF))
                    .border(2.dp, WineBackground, CircleShape)
                    .align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Big Typography Name
        Text(
            text = "Aryan\nMehta.",
            color = WineText,
            fontSize = 50.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 52.sp,
            letterSpacing = (-1.5).sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Title
        Text(
            text = "Technical Founder @ EcoTech",
            color = WineAccent,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Bio
        Text(
            text = "Building sustainable hardware for the next generation. YC '26 hopeful. ☕",
            color = WineOnSurfaceVariant,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            modifier = Modifier.padding(end = 40.dp)
        )
    }
}

@Composable
fun EditorialStatsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        EditorialStat(value = "47", label = "DAY STREAK")
        EditorialStat(value = "1.2k", label = "FOLLOWERS")
        EditorialStat(value = "15", label = "POSTS")
        EditorialStat(value = "3", label = "STARTUPS")
    }
}

@Composable
fun EditorialStat(value: String, label: String) {
    Column {
        Text(
            text = value,
            color = WineText,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = (-1).sp
        )
        Text(
            text = label,
            color = WineOnSurfaceVariant,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditorialSkills() {
    val skills = listOf("Product Strategy", "Kotlin", "Growth Hacking", "UI/UX", "Hardware", "Pitching")
    FlowRow(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        skills.forEach { skill ->
            Box(
                modifier = Modifier
                    .border(1.dp, WineOutline, CircleShape)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = skill.uppercase(),
                    color = WineText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
            }
        }
    }
}

@Composable
fun EditorialAchievements() {
    val achievements = listOf(
        Achievement(Icons.Outlined.LocalFireDepartment, "30 Day Streak", WineWarning),
        Achievement(Icons.Outlined.RocketLaunch, "MVP Launched", WineAccent),
        Achievement(Icons.Outlined.People, "1k Followers", Color(0xFF2DD4BF)),
        Achievement(Icons.Outlined.EmojiEvents, "Top 10% Poster", WinePrimary)
    )

    LazyColumn(
        modifier = Modifier.height((achievements.size * 64).dp), // Fixed height to avoid nested scrolling
        userScrollEnabled = false
    ) {
        items(
            items = achievements,
            key = { it.label }
        ) { ach ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ach.icon,
                    contentDescription = null,
                    tint = ach.color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = ach.label,
                    color = WineText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "UNLOCKED",
                    color = WineOnSurfaceVariant,
                    fontSize = 9.sp,
                    letterSpacing = 1.5.sp
                )
            }
        }
    }
}

@Composable
fun EditorialStartups() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(WineSurfaceVariant, RoundedCornerShape(16.dp))
            .border(1.dp, WineOutline, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.linearGradient(listOf(WinePrimary, WineAccent))),
                contentAlignment = Alignment.Center
            ) {
                Text("ET", color = WineText, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("EcoTech", color = WineText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Hardware · MVP Stage", color = WineOnSurfaceVariant, fontSize = 13.sp)
            }
            Box(
                modifier = Modifier
                    .background(WineAccent.copy(alpha = 0.1f), CircleShape)
                    .border(1.dp, WineAccent.copy(alpha = 0.3f), CircleShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("ACTIVE", color = WineAccent, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = WineAccent,
        fontSize = 10.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 3.sp,
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}

@Composable
fun DividerHairline() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(1.dp)
            .background(WineOutline)
    )
}
