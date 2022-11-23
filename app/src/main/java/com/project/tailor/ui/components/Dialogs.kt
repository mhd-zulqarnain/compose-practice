package com.project.tailor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun showDialog(
    url: String,
    title: String,
    description: String,
    primaryButtonText: String? = null,
    negativeButtonText: String? = null,
    primaryButtonAction: () -> Unit,
    negativeButtonAction: () -> Unit?,
    showDialog: MutableState<Boolean>,
) {
    if (showDialog.value)
        AlertDialog(onDismissRequest = { showDialog.value = false },
            title = {
                Text(text = title)
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(url),
                        contentDescription = title,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(10.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = description)
                }

            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = primaryButtonAction,
                    ) {
                        Text(text = primaryButtonText.orEmpty())
                    }
                }
            }

        )
}
