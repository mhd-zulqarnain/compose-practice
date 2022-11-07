package com.project.tailor

import android.os.Bundle
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
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
                    LaunchedEffect(Unit) {
                        when(productUIState) {
                            is ProfileViewModel.ProductResult.ProductList->{
//                                ProductList(productUIState.list)
                            }
                            // Same as in the question
                        }
                    }

//                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun ProductList(list: List<Product>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(list) { product ->
            ProductCard(product)
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