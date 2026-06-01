package com.foundrly.app.features.ai_chat

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAIEngine @Inject constructor() {
    fun getResponse(input: String): String {
        val lowerInput = input.lowercase()
        return when {
            lowerInput.contains("validate") || lowerInput.contains("idea") -> {
                "Talk to 20 potential users and identify recurring pain points. Use the Mom Test framework — ask about their life, not your idea. Track patterns across conversations before building anything."
            }
            lowerInput.contains("pitch") || lowerInput.contains("deck") -> {
                "A great pitch deck has 10 slides: Problem, Solution, Market Size, Product, Traction, Team, Competition, Financials, and Ask. Keep it simple and visual."
            }
            lowerInput.contains("co-founder") || lowerInput.contains("cofounder") -> {
                "Look for complementary skills. If you're technical, find a business/sales person. Check LinkedIn, AngelList, or your college alumni network. Work on a small project together first to test chemistry."
            }
            lowerInput.contains("fund") || lowerInput.contains("money") -> {
                "Start with bootstrapping if possible. Then look at friends & family, university grants, or pitch competitions. Seed funds and angels want to see some early traction or a strong MVP."
            }
            lowerInput.contains("mom test") -> {
                "A book by Rob Fitzpatrick. The core rule: never ask if someone likes your idea. Instead, ask about their actual behaviour and past experience with the problem. Real validation comes from commitments, not compliments."
            }
            else -> {
                "Great question! Tell me more about your startup's context so I can give you tailored advice."
            }
        }
    }
}
