package com.example.seguitucarreraapp.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seguitucarreraapp.data.remote.SubjectLocalDataSource
import com.example.seguitucarreraapp.data.repository.SubjectRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: SubjectRepository
) : ViewModel() {

    fun subjectsByYear(year: Int) =
        repository.getSubjectsByYear(year)

    fun preloadSubjects(
        context: Context
    ) {
        viewModelScope.launch {
            val dataSource = SubjectLocalDataSource(context)
            val subjects = dataSource.loadSubjects()
            repository.preloadSubjectsFromJson(subjects)
        }
    }


}
