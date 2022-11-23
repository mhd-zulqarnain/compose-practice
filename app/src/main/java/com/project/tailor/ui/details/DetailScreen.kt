package com.project.tailor.ui.details

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import com.project.tailor.ProfileViewModel
import com.project.tailor.model.Product

@Composable
fun DetailsScreen(viewModel: ProfileViewModel, context: Context, navController: NavHostController) {
    Column {
        Text(text = "Details")
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun commentSection(product: Product, viewModel: ProfileViewModel) {
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        textStyle = TextStyle(fontStyle = FontStyle.Normal),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray, shape = RectangleShape),
        onValueChange = {
            text = it
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            if (text.isNotBlank())
                viewModel.addComment(text, product.id)
            text = ""
            keyboardController?.hide()
        }),
        label = { Text("Enter your comment", color = Color.LightGray) })
}