package com.project.tailor.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Loader() {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(40.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
}