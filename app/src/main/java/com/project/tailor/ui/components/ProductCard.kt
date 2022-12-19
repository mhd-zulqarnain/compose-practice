package com.project.tailor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.project.tailor.ProductViewModel
import com.project.tailor.model.Product
import com.project.tailor.ui.Screen

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
                        imageVector = Icons.Default.Share,
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

