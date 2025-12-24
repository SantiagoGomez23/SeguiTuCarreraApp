package com.example.seguitucarreraapp.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seguitucarreraapp.auth.UserSession
import com.example.seguitucarreraapp.data.local.entity.SubjectEntity
import com.example.seguitucarreraapp.data.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: SubjectRepository
) : ViewModel() {

    /**
     * Precarga las materias desde subject.json hacia Room.
     * Se puede llamar al entrar a Home.
     */
    fun preloadSubjects(context: Context) {
        viewModelScope.launch {
            repository.preloadSubjectsFromJson(context)
        }
    }

    /**
     * Devuelve las materias filtradas por año
     */
    fun subjectsByYear(year: Int): Flow<List<SubjectEntity>> {
        return repository.getSubjectsByYear(
            year = year,
            userId = UserSession.uid()
        )
    }
}
