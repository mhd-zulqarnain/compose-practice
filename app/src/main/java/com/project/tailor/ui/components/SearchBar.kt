package com.project.tailor.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeSearch(
    searchInput: String = "",
    onSearchInputChanged: (String) -> Unit,
    onBackPressedCallback: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Surface(
        border = BorderStroke(
            Dp.Hairline, MaterialTheme.colors.secondary.copy(alpha = .6f)
        ),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 9.dp)
        ) {
            IconButton(onClick = {
                onBackPressedCallback(true)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            var text by rememberSaveable { mutableStateOf(searchInput) }
            ProvideTextStyle(TextStyle(color = Color.White)) {
                val keyboardController = LocalSoftwareKeyboardController.current
                TextField(
                    value = text,
                    textStyle = TextStyle(color = Color.White),
                    maxLines = 1,
                    onValueChange = {
                        if (it.length <= 10) {
                            text = it
                            onSearchInputChanged(it)
                        }
                    },
                    modifier = Modifier.focusRequester(focusRequester),
                    placeholder = { Text(text = "Search the products") },

                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
//                                    submitSearch(onSearchInputChanged,context)
                        keyboardController?.hide()
                    })
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
//                    focusRequester.requestFocus()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search, contentDescription = ""
                )
            }
        }
    }
}
