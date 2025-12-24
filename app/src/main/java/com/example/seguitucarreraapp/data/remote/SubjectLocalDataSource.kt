package com.example.seguitucarreraapp.data.remote

import android.content.Context
import com.example.seguitucarreraapp.data.remote.dto.SubjectDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubjectLocalDataSource(
    private val context: Context
) {

    fun loadSubjects(): List<SubjectDto> {
        val json = JsonReader.readFromAssets(context, "subjects.json")
        val type = object : TypeToken<List<SubjectDto>>() {}.type
        return Gson().fromJson(json, type)
    }
}
