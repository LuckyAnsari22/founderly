package com.foundrly.app.features.community

import androidx.lifecycle.ViewModel
import com.foundrly.app.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    init {
        _posts.value = listOf(
            Post(1, "https://images.unsplash.com/photo-1534528741775-53994a69daeb?q=80&w=200&auto=format&fit=crop", "NutriAI", "Beta", "Just closed our first pilot with 3 college canteens. The hustle is real \uD83D\uDE80", 24, 8, "2h ago"),
            Post(2, "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?q=80&w=200&auto=format&fit=crop", "CampusRide", "MVP", "Looking for a CTO with Kotlin + React Native experience. DM me if interested \uD83D\uDC40", 41, 15, "4h ago"),
            Post(3, "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=200&auto=format&fit=crop", "StudyMate", "Growth", "Hit 500 users in week 1 with zero ad spend. Organic always beats paid at early stage.", 89, 22, "6h ago"),
            Post(4, "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=200&auto=format&fit=crop", "GreenThread", "Idea", "Failed our first investor pitch. Here is what we learned — thread coming soon.", 112, 34, "1d ago"),
            Post(5, "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?q=80&w=200&auto=format&fit=crop", "MediQuick", "Seed", "Our landing page went from 2% to 11% conversion after one Lean Canvas session.", 67, 19, "1d ago")
        )
    }

    fun toggleLike(postId: Int) {
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                val newIsLiked = !post.isLiked
                val newLikes = if (newIsLiked) post.likes + 1 else post.likes - 1
                post.copy(isLiked = newIsLiked, likes = newLikes)
            } else {
                post
            }
        }
    }
}
