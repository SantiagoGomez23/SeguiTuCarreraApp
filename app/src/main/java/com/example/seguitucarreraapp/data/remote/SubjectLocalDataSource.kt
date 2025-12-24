package com.example.seguitucarreraapp.data.remote

import android.content.Context
import com.google.gson.Gson

class SubjectLocalDataSource {

    fun loadSubjects(context: Context): List<SubjectDto> {
        val json = context.assets
            .open("subject.json")
            .bufferedReader()
            .use { it.readText() }

        val response = Gson().fromJson(
            json,
            SubjectsResponseDto::class.java
        )

        return response.subjects
    }
}
