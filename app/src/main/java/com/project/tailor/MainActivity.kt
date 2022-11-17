@file:OptIn(ExperimentalComposeUiApi::class)

package com.project.tailor

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.project.tailor.model.Product
import com.project.tailor.ui.theme.TailorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ProfileViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProducts()
        setContent {
            TailorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    Scaffold(
                        topBar = {
                        }
                    ) {
                        val productUIState by viewModel.productResult.collectAsState()
                        ProductList(productUIState = productUIState, this@MainActivity)
                    }
                }

            }
        }
    }
}

@Composable
fun ProductList(productUIState: ProfileViewModel.ProductResult, context: Context) {
    when (productUIState) {
        is ProfileViewModel.ProductResult.ProductList -> {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(productUIState.list) { product ->
                    ProductCard(product)
                }
            }
        }
        is ProfileViewModel.ProductResult.Error -> {
            ErrorCard(productUIState.error)
        }
        is ProfileViewModel.ProductResult.Loading -> {
            loader()
        }
    }

}

@Composable
fun loader() {
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

@Composable
fun ProductCard(product: Product) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        showDialog(
            product.images[0]?.orEmpty(),
            product.title.orEmpty(),
            product.description.orEmpty(),
            "Ok", "cancel", {
                showDialog.value = false
            }, {},
            showDialog
        )
    }
    Column(
        Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = {
                showDialog.value = true
            })
        ) {
            Image(
                painter = rememberImagePainter(product.images[0]),
                contentDescription = product.title,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier.padding(8.dp)
            ) {
                Text(text = product.title.orEmpty())
                Text(text = product.description.orEmpty())
            }
        }
        //comment/like section
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
                text = ""
                keyboardController?.hide()
            }),
            label = { Text("Enter your comment", color = Color.LightGray) })
    }

}

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProductCard(Product())
}