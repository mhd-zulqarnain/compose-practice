package com.project.tailor

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.project.tailor.model.Product
import com.project.tailor.ui.theme.TailorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProducts()
        setContent {
            TailorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val productUIState by viewModel.productResult.collectAsState()
                    ProductList(productUIState = productUIState, this@MainActivity)
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = productUIState.error,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        is ProfileViewModel.ProductResult.Loading -> {
//            todo : progress bar handling
//            CircularProgressIndicator(
//                context
//            )
        }
    }

}

@Composable
fun ProductCard(product: Product) {
    Row(
        verticalAlignment = Alignment.CenterVertically
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
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProductCard(Product())
}