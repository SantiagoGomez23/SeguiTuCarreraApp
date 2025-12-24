package com.example.seguitucarreraapp.data.local

import android.content.Context
import com.example.seguitucarreraapp.data.remote.SubjectDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubjectLocalDataSource {

    fun loadSubjects(context: Context): List<SubjectDto> {
        val json = context.assets.open("subject.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<SubjectDto>>() {}.type
        return Gson().fromJson(json, type)
    }
}
