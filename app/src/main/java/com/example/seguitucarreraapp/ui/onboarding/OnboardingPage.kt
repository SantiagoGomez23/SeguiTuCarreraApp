package com.example.seguitucarreraapp.ui.onboarding

import androidx.annotation.DrawableRes

data class OnboardingPage(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int?,
    val showLogo: Boolean = false
)
