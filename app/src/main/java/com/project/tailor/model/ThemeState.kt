package com.project.tailor.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object ThemeState {
    var isLight by mutableStateOf(true)
}