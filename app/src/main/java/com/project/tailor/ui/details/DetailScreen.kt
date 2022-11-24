package com.project.tailor.ui.details

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.tailor.ProductViewModel
import com.project.tailor.model.Product
import com.project.tailor.ui.home.ErrorCard
import com.project.tailor.ui.home.ProductCard
import java.text.DateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: ProductViewModel,
    context: Context,
    navController: NavHostController,
    product: Product?
) {
    viewModel.getComments(product?.id)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = product?.title.orEmpty()) },
            navigationIcon = if (navController.previousBackStackEntry != null) {
                {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            } else {
                null
            }

        )
    }) {
        if (product == null)
            ErrorCard("Something went wrong")
        else
            Column {
                ProductCard(product = product, viewModel = viewModel)
                commentSection(product = product, viewModel = viewModel)
                comments(viewModel, context)
            }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun commentSection(product: Product, viewModel: ProductViewModel) {
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

@Composable
fun comments(viewModel: ProductViewModel, context: Context) {
    val comments by viewModel.commentResult.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(comments) { comment ->
                Column(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = DateFormat.getInstance().format(comment.timeStamp).toString(),
                        fontSize = 10.sp
                    )
                    Row() {
                        Text(
                            modifier = Modifier.weight(3f),
                            text = comment.comment,
                            fontSize = 16.sp
                        )
                        IconButton(
                            onClick = {
                                viewModel.deleteComment(comment.productId,comment.id!!)
                            },
                            modifier = Modifier.weight(1f).size(16.dp),
                        ) {
                            Icon(imageVector = Icons.Filled.Delete, "delete")
                        }
                    }

                }
            }
        }
    }
}