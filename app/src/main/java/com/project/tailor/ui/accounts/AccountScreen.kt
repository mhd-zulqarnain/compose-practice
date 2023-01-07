package com.project.tailor.ui.accounts

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.tailor.accountsheader.AccountsHeader

@Composable
fun AccountScreen(
    context: Context,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            , verticalArrangement = Arrangement.Center
    ) {
        AccountsHeader(tvTitle = "Coming soon", tvDes = "Account Details", modifier = Modifier.height(120.dp) , bgAccountsHeader = Color.White)
    }
}
@Preview
@Composable
fun preview(){
    AccountsHeader(tvTitle = "Coming soon", tvDes = "Account Details", modifier = Modifier.height(120.dp) , bgAccountsHeader = Color.White)
}