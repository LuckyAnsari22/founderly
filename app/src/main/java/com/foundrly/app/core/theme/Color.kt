package com.foundrly.app.core.theme

import androidx.compose.ui.graphics.Color

// ── Dark Wine Brand Palette ───────────────────────────────────────────────────
val WineBackground     = Color(0xFF0B0B0F)   // Deep black-charcoal
val WineSecondary      = Color(0xFF121217)   // Dark secondary surface
val WinePrimary        = Color(0xFF6E0F1A)   // Rich deep wine
val WineAccent         = Color(0xFFFF3B5C)   // Vivid rose-red accent
val WineText           = Color(0xFFFFFFFF)   // Pure white text

// ── Surface variants ─────────────────────────────────────────────────────────
val WineSurface        = Color(0xFF1A1020)   // Slightly elevated surface
val WineSurfaceVariant = Color(0xFF221525)   // Card / container background
val WineOutline        = Color(0xFF3A1E28)   // Subtle wine-tinted border
val WineOutlineVariant = Color(0xFF2A1520)   // Even subtler divider

// ── Text variants ─────────────────────────────────────────────────────────────
val WineOnSurface         = Color(0xFFFFFFFF)
val WineOnSurfaceVariant  = Color(0xFFB0899A)   // Muted rose-grey for secondary text
val WineOnBackground      = Color(0xFFFFFFFF)

// ── Semantic ──────────────────────────────────────────────────────────────────
val WineSuccess        = Color(0xFF2DD4BF)   // Teal – stands out on dark wine
val WineWarning        = Color(0xFFF59E0B)   // Amber
val WineError          = Color(0xFFFF3B5C)   // Same as accent for errors

// ── Gradient helpers ──────────────────────────────────────────────────────────
val WineGradientStart  = Color(0xFF6E0F1A)
val WineGradientEnd    = Color(0xFFFF3B5C)
val WineGlassLight     = Color(0x1AFF3B5C)   // Translucent accent for glassmorphism
val WineGlassBorder    = Color(0x33FF3B5C)   // Border for glass cards

// ── Backwards-compat aliases (used across screens without rename) ─────────────
val md_theme_light_background       = WineBackground
val md_theme_light_surface          = WineSurface
val md_theme_light_primary          = WineAccent          // accent is the "action" color
val md_theme_light_secondary        = WinePrimary         // deep wine for secondary
val md_theme_light_onPrimary        = WineText
val md_theme_light_onSecondary      = WineText
val md_theme_light_onBackground     = WineOnBackground
val md_theme_light_onSurface        = WineOnSurface
val md_theme_light_onSurfaceVariant = WineOnSurfaceVariant
val md_theme_light_outline          = WineOutline
val md_theme_light_outlineVariant   = WineOutlineVariant
val md_theme_light_error            = WineError
val md_theme_light_onError          = WineText

val md_theme_dark_background        = WineBackground
val md_theme_dark_surface           = WineSurface
val md_theme_dark_primary           = WineAccent
val md_theme_dark_secondary         = WinePrimary
val md_theme_dark_onPrimary         = WineText
val md_theme_dark_onSecondary       = WineText
val md_theme_dark_onBackground      = WineOnBackground
val md_theme_dark_onSurface         = WineOnSurface
val md_theme_dark_onSurfaceVariant  = WineOnSurfaceVariant
val md_theme_dark_outline           = WineOutline
val md_theme_dark_outlineVariant    = WineOutlineVariant
val md_theme_dark_error             = WineError
val md_theme_dark_onError           = WineText
