package com.project.tailor.ui.home

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.project.tailor.ProductViewModel
import com.project.tailor.model.Product
import com.project.tailor.ui.Screen
import com.project.tailor.ui.components.showDialog


@Composable
fun HomeScreen(viewModel: ProductViewModel, context: Context, navController: NavHostController) {
    Column {
        HomeSearch(searchInput = "", onSearchInputChanged = {
            viewModel.filterProducts(it)
        }, {
            viewModel.filterProducts("", it)
        })
        val productUIState by viewModel.productResult.collectAsState()
        ProductList(productUIState = productUIState, context, viewModel, navController)
    }

}


@Composable
fun ProductList(
    productUIState: ProductViewModel.ProductResult,
    context: Context,
    viewModel: ProductViewModel,
    navController: NavHostController
) {


    when (productUIState) {
        is ProductViewModel.ProductResult.ProductList -> {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(productUIState.list) { product ->
                    ProductCard(product, viewModel, navController)
                }
            }
        }
        is ProductViewModel.ProductResult.Error -> {
            ErrorCard(productUIState.error)
        }
        is ProductViewModel.ProductResult.Loading -> {
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
    onSearchInputChanged: (String) -> Unit,
    onFilter: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Surface(
        border = BorderStroke(
            Dp.Hairline,
            MaterialTheme.colors.secondary.copy(alpha = .6f)
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
//                    focusRequester.requestFocus()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = ""
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
                    keyboardActions = KeyboardActions(
                        onSearch = {
//                                    submitSearch(onSearchInputChanged,context)
                            keyboardController?.hide()
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box {
                var mDisplayMenu by remember { mutableStateOf(false) }
                var filter by remember { mutableStateOf(false) }
                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "stringResource(R.string.cd_more_actions)"
                    )
                }
                // Creating a dropdown menu
                DropdownMenu(
                    expanded = mDisplayMenu,
                    onDismissRequest = { mDisplayMenu = false }
                ) {

                    DropdownMenuItem(onClick = { }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                filter = !filter
                                onFilter(filter)
                            }
                        ) {
                            Checkbox(checked = filter, onCheckedChange = {
                                filter = !filter
                                onFilter(filter)
                            })
                            Text(text = "Filter by favorite")
                        }
                    }
                    Text(text = "clear Filter",
                        Modifier
                            .padding(65.dp, 0.dp)
                            .clickable {
                                filter = false
                                onSearchInputChanged("")
                            })
                }
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductCard(
    product: Product,
    viewModel: ProductViewModel,
    navController: NavHostController? = null
) {
    val showDialog = remember { mutableStateOf(false) }
    val favoriteIcon = if (product.favorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder
    if (showDialog.value) {
        showDialog(
            product.images[(0 until (product.images.size)).random()],
            product.title,
            product.description.orEmpty(),
            "Ok", "cancel", {
                showDialog.value = false
            }, {},
            showDialog
        )
    }
    Card(
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .clickable(onClick = {
                navController?.let {
                    viewModel.setProductDetails(product)
                    navController.navigate(Screen.Details.toString())
                }
            }),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(if (navController != null) 120.dp else 180.dp)
                    .defaultMinSize(minHeight = 150.dp),
                shape = RoundedCornerShape(8.dp, 16.dp, 8.dp, 16.dp),
            ) {
                Image(
                    painter = rememberImagePainter(product.images[0]),
                    contentDescription = product.title,
                    modifier = Modifier.size(1000.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Row {
                IconButton(
                    onClick = {
                        viewModel.toggleFavorite(product)
                    },
                    modifier = Modifier
                        .size(25.dp)
                ) {
                    Icon(
                        imageVector = favoriteIcon,
                        "favorite",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(
                    onClick = {
                        showDialog.value = true

                    },
                    modifier = Modifier
                        .size(25.dp),
                ) {
                    Icon(
                        imageVector =  Icons.Default.Share,
                        "favorite",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    Modifier.padding(8.dp)
                ) {
                    Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = product.description.orEmpty(), fontSize = 14.sp, maxLines = 4)

                }
            }
        }
    }
}

