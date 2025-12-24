package com.example.seguitucarreraapp.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seguitucarreraapp.data.repository.SubjectRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: SubjectRepository
) : ViewModel() {

    private var initialized = false

    fun preloadSubjects(context: Context) {
        if (initialized) return

        viewModelScope.launch {
            repository.preloadSubjectsFromJson(context)
            initialized = true
        }
    }

    fun subjectsFirstYear() =
        repository.getSubjectsByYear(1)
}
