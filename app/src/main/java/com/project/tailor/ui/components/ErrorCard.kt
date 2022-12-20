package com.project.tailor.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorCard(error: String) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Text(text = "Error")
            Text(text = error)
        }

    }
}