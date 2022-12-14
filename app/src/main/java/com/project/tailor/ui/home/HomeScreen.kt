package com.project.tailor.ui.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.tailor.ProductViewModel
import com.project.tailor.ui.Screen
import com.project.tailor.ui.components.*
import kotlinx.coroutines.launch
import javax.annotation.meta.When


@Composable
fun HomeScreen(viewModel: ProductViewModel, context: Context, navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Column {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppTopBar(viewModel, navIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
            }, drawerContent = {
                DrawerHeader()
                DrawerBody(items = listOf(
//                    MenuItems(Screen.Home, Icons.Default.Home, "Home"),
                    MenuItems(Screen.Account, Icons.Default.Person, "Accounts")
                ), onClickItem = {
                    //close drawer
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    when (it.screen) {
                        Screen.Home -> {
                            navController.navigate(Screen.Details.toString())
                        }
                        Screen.Account -> {
                            navController.navigate(Screen.Account.toString())
                        }
                        else->{
                            //not required
                        }
                    }
                    println("clicked ${it.title}")
                })
            }) {
            val productUIState by viewModel.productResult.collectAsState()
            ProductList(productUIState = productUIState, context, viewModel, navController)
        }
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




