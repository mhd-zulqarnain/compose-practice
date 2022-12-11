package com.project.tailor.ui.details

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.project.tailor.ProductViewModel
import com.project.tailor.model.Product
import com.project.tailor.ui.home.ErrorCard
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
    Surface(color = MaterialTheme.colors.background) {

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            containerColor = MaterialTheme.colors.background,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = {
                        Text(text =  product?.title.orEmpty())
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colors.background,
                        scrolledContainerColor = MaterialTheme.colors.onSurface
                    ),
                    scrollBehavior = scrollBehavior,
                    navigationIcon =
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
//            topBar = {
//                TopAppBar(
//                    title = { Text(text = product?.title.orEmpty()) },
//                    navigationIcon = if (navController.previousBackStackEntry != null) {
//                        {
//                            IconButton(onClick = { navController.navigateUp() }) {
//                                Icon(
//                                    imageVector = Icons.Filled.ArrowBack,
//                                    contentDescription = "Back"
//                                )
//                            }
//                        }
//                    } else {
//                        null
//                    }
//                    )
//            }
        ) {
//                comments(viewModel, context)
//
//
            if (product == null)
                ErrorCard("Something went wrong")
            else
                productDetails(product = product, viewModel = viewModel, context = context)
        }
//        {
//            if (product == null)
//                ErrorCard("Something went wrong")
//            else
//                    ProductCard(product = product, viewModel = viewModel)
//            Column {
//                ProductCard(product = product, viewModel = viewModel)
//                commentSection(product = product, viewModel = viewModel)
//                comments(viewModel, context)
//            }
//        }
    }
}

@Composable
fun productDetails(
    product: Product,
    viewModel: ProductViewModel,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(346.dp),
            painter = rememberImagePainter(product.images[0]),
            alignment = Alignment.CenterStart,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        commentSection(product = product, viewModel = viewModel)
        comments(viewModel, context)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun commentSection(product: Product, viewModel: ProductViewModel) {

    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = text,
        textStyle = TextStyle(
            fontStyle = FontStyle.Normal,
            color = androidx.compose.material.MaterialTheme.colors.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
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
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
//        modifier = Modifier
//            .then(
//                Modifier.disableVerticalPointerInputScroll()
//            )
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
                            viewModel.deleteComment(comment.productId, comment.id!!)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .size(16.dp),
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, "delete")
                    }
                }

            }
        }
    }
}