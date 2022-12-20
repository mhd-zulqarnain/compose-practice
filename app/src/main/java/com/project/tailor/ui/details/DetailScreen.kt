package com.project.tailor.ui.details

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.project.tailor.ProductViewModel
import com.project.tailor.model.Product
import com.project.tailor.ui.components.ProductDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: ProductViewModel,
    context: Context,
    navController: NavHostController,
    product: Product
) {
    viewModel.getComments(product.id)
    Surface(color = MaterialTheme.colors.background) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold(
            containerColor = MaterialTheme.colors.background,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = {
                        Text(text = product.title)
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colors.secondary,
                        scrolledContainerColor = MaterialTheme.colors.secondary
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
        ) {
                ProductDetails(product = product, viewModel = viewModel, context = context)
        }
    }
}
