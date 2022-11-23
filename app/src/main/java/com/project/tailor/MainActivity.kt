@file:OptIn(ExperimentalComposeUiApi::class)

package com.project.tailor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.tailor.ui.Screen
import com.project.tailor.ui.details.DetailsScreen
import com.project.tailor.ui.home.HomeScreen
import com.project.tailor.ui.home.HomeSearch
import com.project.tailor.ui.theme.TailorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ProductViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProducts()
        setContent {
            TailorTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Screen.Home.toString()) {
                    composable(route = Screen.Home.toString()) {
                        HomeScreen(viewModel, this@MainActivity, navController)
                    }
                    composable(route = Screen.Details.toString()) {
                        val product by viewModel.productDetails.collectAsState()
                        DetailsScreen(viewModel, this@MainActivity, navController,product)
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeSearch("Home search", {})
}