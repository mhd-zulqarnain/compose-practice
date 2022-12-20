package com.project.tailor.ui.components

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.project.tailor.ProductViewModel
import com.project.tailor.model.Product
import java.text.DateFormat
import androidx.compose.material.Text

@Composable
fun ProductDetails(
    product: Product,
    viewModel: ProductViewModel,
    context: Context
) {
    val comments by viewModel.commentResult.collectAsState()
    LazyColumn {
        item {
            ImageWithLikeIcon(
                product, viewModel, modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
        }
        item {
            commentSection(product = product, viewModel = viewModel)
        }
        items(comments) { comment ->
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = DateFormat.getInstance().format(comment.timeStamp).toString(),
                    fontSize = 11.sp
                )
                Row {
                    Text(
                        modifier = Modifier.weight(3f),
                        text = comment.comment,
                        fontSize = 18.sp
                    )
                    IconButton(
                        onClick = {
                            viewModel.deleteComment(comment.productId, comment.id!!)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .size(20.dp),
                    ) {
                        Icon(imageVector = Icons.Default.Delete, "delete")
                    }
                }

            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun commentSection(product: Product, viewModel: ProductViewModel) {

    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp)
    ) {
        Text(text = product.description.orEmpty(), fontSize = 16.sp, maxLines = 4)

        OutlinedTextField(
            value = text,
            textStyle = TextStyle(
                fontStyle = FontStyle.Normal,
                color = MaterialTheme.colors.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth(),
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

}


@Composable
fun ImageWithLikeIcon(
    product: Product,
    viewModel: ProductViewModel, modifier: Modifier
) {
    val favoriteIcon = if (product.favorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder
    ConstraintLayout(modifier = modifier) {
        val (icon, image) = createRefs()
        Image(
            painter = rememberImagePainter(product.images[0]),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                    height = Dimension.value(250.dp)
                },
            contentScale = ContentScale.Crop,
            alignment = Alignment.CenterStart,
        )
        IconButton(
            onClick = {
                viewModel.toggleFavorite(product)
            },
            modifier = Modifier
                .size(25.dp)
                .constrainAs(icon) {
                    top.linkTo(image.top, margin = 8.dp)
                    end.linkTo(image.end, margin = 8.dp)
                },
        ) {
            Icon(
                imageVector = favoriteIcon,
                "favorite",
                tint = Color.Red
            )
        }

    }
}