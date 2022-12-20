package com.project.tailor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTopBar(
    navIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "top bar", style = TextStyle(color = MaterialTheme.colors.onPrimary))
        },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            IconButton(onClick = navIconClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
            }
        }
    )

}

@Composable
fun DrawerHeader() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.secondary)
        .padding(64.dp)) {
        Text(text = "The drawer header ", style = TextStyle(fontSize = 20.sp , color = MaterialTheme.colors.onSecondary))
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
    var id: String,
    var icon: ImageVector,
    var title: String = ""
)