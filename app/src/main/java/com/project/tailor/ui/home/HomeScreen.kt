package com.project.tailor.ui.home

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.project.tailor.ProfileViewModel
import com.project.tailor.model.Product
import com.project.tailor.ui.Screen
import com.project.tailor.ui.components.showDialog


@Composable
fun HomeScreen(viewModel: ProfileViewModel, context: Context, navController: NavHostController) {
    Column {
        HomeSearch(searchInput = "", onSearchInputChanged = {
            viewModel.filterProducts(it)
        })
        val productUIState by viewModel.productResult.collectAsState()
        ProductList(productUIState = productUIState, context, viewModel,navController)
    }

}


@Composable
fun ProductList(
    productUIState: ProfileViewModel.ProductResult,
    context: Context,
    viewModel: ProfileViewModel,
    navController: NavHostController
) {


    when (productUIState) {
        is ProfileViewModel.ProductResult.ProductList -> {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(productUIState.list) { product ->
                    ProductCard(product, viewModel,navController)
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



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeSearch(
    searchInput: String = "",
    onSearchInputChanged: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(9.dp),
        border = BorderStroke(
            Dp.Hairline,
            MaterialTheme.colors.onSurface.copy(alpha = .6f)
        ),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 9.dp)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            ProvideTextStyle(TextStyle(color = Color.White)) {
                var text by rememberSaveable { mutableStateOf(searchInput) }
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
                    placeholder = { Text(text = "Search the products") },

                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
//                                    submitSearch(onSearchInputChanged,context)
                            keyboardController?.hide()
                        }
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* Functionality not supported yet */ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "stringResource(R.string.cd_more_actions)"
                )
            }
        }

    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductCard(product: Product, viewModel: ProfileViewModel, navController: NavHostController) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        showDialog(
            product.images[(0 until (product.images.size)).random()],
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
        val image = (0 until (product.images.size)).random()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = {
//                showDialog.value = true
                navController.navigate(Screen.Details.toString())
            })
        ) {
            Image(
                painter = rememberImagePainter(product.images[image]),
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
//        commentSection(product, viewModel)
    }

}

