@file:OptIn(ExperimentalComposeUiApi::class)

package com.project.tailor

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.tailor.ui.Screen
import com.project.tailor.ui.accounts.AccountScreen
import com.project.tailor.ui.details.DetailsScreen
import com.project.tailor.ui.home.HomeScreen
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
                NavHost(viewModel, this@MainActivity, navController)
            }
        }
    }
}


@Composable
fun NavHost(viewModel: ProductViewModel, context: Context, navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.toString()) {
        composable(route = Screen.Home.toString()) {
            HomeScreen(viewModel, context, navController)
        }
        composable(route = Screen.Details.toString()) {
            val product by viewModel.productDetails.collectAsState()
            product?.let {
                DetailsScreen(viewModel, context, navController, it)
            }
        }
        composable(route = Screen.Account.toString()) {
            AccountScreen(context, navController)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    ConstraintLayout {

        val (text, image) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier.constrainAs(image){
                top.linkTo(parent.top, margin = 14.dp)
            }.size(56.dp)
        )
        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(image.top, margin = 4.dp)
            end.linkTo(image.end , margin = 4.dp)
        })
    }
}