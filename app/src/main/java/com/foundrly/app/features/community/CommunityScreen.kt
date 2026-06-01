package com.foundrly.app.features.community

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.foundrly.app.core.theme.WineAccent
import com.foundrly.app.core.theme.WineBackground
import com.foundrly.app.core.theme.WineGlassBorder
import com.foundrly.app.core.theme.WineGlassLight
import com.foundrly.app.core.theme.WineOnSurfaceVariant
import com.foundrly.app.core.theme.WineOutline
import com.foundrly.app.core.theme.WinePrimary
import com.foundrly.app.core.theme.WineSurfaceVariant
import com.foundrly.app.core.theme.WineText
import com.foundrly.app.data.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(viewModel: FeedViewModel = hiltViewModel()) {
    val posts by viewModel.posts.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Trending", "Following", "Events")
    
    val contentAlpha = remember { androidx.compose.animation.core.Animatable(0f) }
    val contentSlide = remember { androidx.compose.animation.core.Animatable(60f) }

    LaunchedEffect(Unit) {
        contentAlpha.animateTo(1f, tween(500, easing = androidx.compose.animation.core.EaseOutCubic))
        contentSlide.animateTo(0f, tween(500, easing = androidx.compose.animation.core.EaseOutCubic))
    }

    Scaffold(containerColor = WineBackground) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(WineBackground)
                .graphicsLayer {
                    alpha = contentAlpha.value
                    translationY = contentSlide.value
                },
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // ── HEADER ─────────────────────────────────────────────────────
            item {
                Column(
                    modifier = Modifier
                        .background(WineBackground)
                        .padding(horizontal = 24.dp)
                        .padding(top = 52.dp, bottom = 16.dp)
                ) {
                    // Overline
                    Text(
                        text = "FOUNDER FEED",
                        color = WineAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 3.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "The Pulse.",
                            color = WineText,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-1).sp,
                            modifier = Modifier.weight(1f)
                        )
                        // Post CTA — top right
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    Brush.horizontalGradient(listOf(WinePrimary, WineAccent))
                                )
                                .clickable { }
                                .padding(horizontal = 14.dp, vertical = 9.dp)
                        ) {
                            Text(
                                "✦ Post",
                                color = WineText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // ── FILTER ROW — minimal tab style ─────────────────────────────
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(filters) { filter ->
                        val isSelected = filter == selectedFilter
                        val textColor by animateColorAsState(
                            targetValue = if (isSelected) WineAccent else WineOnSurfaceVariant,
                            label = "filterColor"
                        )
                        Column(
                            modifier = Modifier
                                .clickable { selectedFilter = filter }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = filter,
                                color = textColor,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            // Underline indicator — editorial tab style
                            Box(
                                modifier = Modifier
                                    .width(if (isSelected) 20.dp else 0.dp)
                                    .height(2.dp)
                                    .background(WineAccent, CircleShape)
                            )
                        }
                    }
                }
                // Full-width hairline under tabs
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(WineOutline)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ── POSTS ───────────────────────────────────────────────────────
            itemsIndexed(posts, key = { _, post -> post.id }) { index, post ->
                EditorialPostCard(
                    post = post,
                    onLikeClick = { viewModel.toggleLike(post.id) }
                )
                // Hairline divider between posts (not after last)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(1.dp)
                        .background(WineOutline)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun EditorialPostCard(post: Post, onLikeClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        // ── BYLINE ROW ─────────────────────────────────────────────────────
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar — no rounded box, just a circle
            Box {
                AsyncImage(
                    model = post.avatarUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, WineGlassBorder, CircleShape)
                )
                // Tiny online dot
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2DD4BF))
                        .border(1.5.dp, WineBackground, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = post.startupName,
                    color = WineText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${post.stage.uppercase()}  ·  ${post.timestamp}",
                    color = WineOnSurfaceVariant,
                    fontSize = 10.sp,
                    letterSpacing = 0.5.sp
                )
            }

            // Stage tag — subtle right pill
            Box(
                modifier = Modifier
                    .background(WineAccent.copy(alpha = 0.09f), CircleShape)
                    .border(1.dp, WineAccent.copy(alpha = 0.25f), CircleShape)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = post.stage,
                    color = WineAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ── POST CONTENT ────────────────────────────────────────────────────
        Text(
            text = post.content,
            color = WineText.copy(alpha = 0.88f),
            fontSize = 15.sp,
            lineHeight = 24.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Continue reading →",
            color = WineAccent,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ── ACTION ROW ──────────────────────────────────────────────────────
        Row(verticalAlignment = Alignment.CenterVertically) {
            AnimatedLikeButton(
                isLiked = post.isLiked,
                count = post.likes,
                onClick = onLikeClick
            )
            Spacer(modifier = Modifier.width(22.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { }
            ) {
                Icon(
                    Icons.Outlined.ChatBubbleOutline,
                    contentDescription = null,
                    tint = WineOnSurfaceVariant,
                    modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "${post.comments}",
                    color = WineOnSurfaceVariant,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Outlined.Share,
                contentDescription = null,
                tint = WineOnSurfaceVariant,
                modifier = Modifier
                    .size(17.dp)
                    .clickable { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AnimatedLikeButton(isLiked: Boolean, count: Int, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val color by animateColorAsState(
        targetValue = if (isLiked) WineAccent else WineOnSurfaceVariant,
        animationSpec = tween(200),
        label = "likeColor"
    )
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.3f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "likeScale"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick() }
    ) {
        Icon(
            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(17.dp).scale(scale)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text("$count", color = color, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

// Backward-compat
@Composable
fun PostCard(post: Post, onLikeClick: () -> Unit) =
    EditorialPostCard(post, onLikeClick)

@Composable
fun LikeButton(isLiked: Boolean, likesCount: Int, onClick: () -> Unit) =
    AnimatedLikeButton(isLiked, likesCount, onClick)
