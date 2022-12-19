package com.project.tailor.ui.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.tailor.ProductViewModel
import com.project.tailor.ui.components.ErrorCard
import com.project.tailor.ui.components.HomeSearch
import com.project.tailor.ui.components.Loader
import com.project.tailor.ui.components.ProductCard


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
            Loader()
        }
    }

}




