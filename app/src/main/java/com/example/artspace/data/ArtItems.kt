package com.example.artspace.data

import android.content.res.Resources
import com.example.artspace.R
import com.example.artspace.vo.Art
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStreamReader

fun readArtItems(resources: Resources): List<Art> {
    // I known, it's wrong. File reading should be done within another thread :)
    return try {
        resources.openRawResource(R.raw.paintings).use { inputStream ->
            InputStreamReader(inputStream).use { isr ->
                val gson = Gson()
                val listType = object : TypeToken<List<Art>>() {}.type

                gson.fromJson(isr, listType)
            }
        }
    } catch (ex: IOException) {
        emptyList()
    }
}