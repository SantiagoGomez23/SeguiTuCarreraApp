package com.example.seguitucarreraapp.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seguitucarreraapp.data.preferences.OnboardingPreferences
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val prefs: OnboardingPreferences
) : ViewModel() {

    fun completeOnboarding() {
        viewModelScope.launch {
            prefs.setCompleted()
        }
    }
}
