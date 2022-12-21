package com.project.tailor.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.tailor.ProductViewModel
import com.project.tailor.ui.Screen

@Composable
fun AppTopBar(
    viewModel: ProductViewModel, navIconClick: () -> Unit
) {
    var visible by remember {
        mutableStateOf(true)
    }
    AnimatedVisibility(visible = visible) {
        TopAppBar(title = {
            Text(
                text = "Home",
                style = TextStyle(color = MaterialTheme.colors.onPrimary),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }, backgroundColor = MaterialTheme.colors.primary, navigationIcon = {
            Row {
                IconButton(onClick = navIconClick) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                }
            }

        }, actions = {
            Box(contentAlignment = Alignment.Center) {
                IconButton(onClick = {
                    visible = false
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            }
        })
    }
    AnimatedVisibility(visible = !visible) {
        HomeSearch(searchInput = "", onSearchInputChanged = {
            viewModel.filterProducts(it)
        }, {
            viewModel.filterProducts("", it)
        }, { onBackPress ->
            visible = onBackPress
        })
    }
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.secondary)
            .padding(64.dp)
    ) {
        Text(
            text = "The drawer header ",
            style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colors.onSecondary)
        )
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItems>,
    textStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onClickItem: (MenuItems) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(12.dp)) {
        items(items) { item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .clickable { onClickItem(item) }) {
                Icon(imageVector = item.icon, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.title, modifier = Modifier.weight(1f), style = textStyle)
            }
        }
    }
}


data class MenuItems(
    var screen: Screen, var icon: ImageVector, var title: String = ""
)