package com.foundrly.app.features.ai_chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foundrly.app.data.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val mockAIEngine: MockAIEngine
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMessage = Message(
            id = _messages.value.size + 1,
            content = text,
            isUser = true,
            timestamp = getCurrentTime()
        )
        _messages.value = _messages.value + userMessage

        viewModelScope.launch {
            _isTyping.value = true
            delay(1000) // Simulating network delay

            val response = mockAIEngine.getResponse(text)
            val aiMessage = Message(
                id = _messages.value.size + 2,
                content = response,
                isUser = false,
                timestamp = getCurrentTime()
            )
            
            _messages.value = _messages.value + aiMessage
            _isTyping.value = false
        }
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    }
}
