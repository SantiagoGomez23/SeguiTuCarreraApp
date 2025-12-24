package com.example.seguitucarreraapp.data.remote

import android.content.Context

object JsonReader {

    fun readFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
    }
}
