package com.example.artspace.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Art(
    val name: String,
    val author: String,
    val year: String,
    val file: String,
    val info: String
) : Parcelable