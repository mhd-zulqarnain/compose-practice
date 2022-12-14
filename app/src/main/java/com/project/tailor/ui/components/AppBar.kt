package com.project.tailor.ui.components

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.tailor.ProductViewModel
import com.project.tailor.model.ThemeState

@Composable
fun AppTopBar(
    viewModel: ProductViewModel, navIconClick: () -> Unit
) {
    var mDisplayMenu by remember { mutableStateOf(false) }
    var filter by remember { mutableStateOf(false) }
    var darkTheme by remember { mutableStateOf(!ThemeState.isLight) }
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
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint  = MaterialTheme.colors.surface
                    )
                }
            }

        }, actions = {
            Box(contentAlignment = Alignment.Center) {

                Row {
                    IconButton(onClick = {
                        visible = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint  = MaterialTheme.colors.surface
                        )
                    }
                    IconButton(onClick = {
                        mDisplayMenu = !mDisplayMenu
                    }) {
                        Box {
                            //filter drop down menu
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "",
                                tint  = MaterialTheme.colors.surface
                            )
                            DropdownMenu(
                                expanded = mDisplayMenu,
                                onDismissRequest = { mDisplayMenu = false }) {
                                DropdownMenuItem(onClick = { }) {
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable {
                                                filter = !filter
                                                viewModel.filterProducts("", filter = filter)

                                            }) {
                                            Checkbox(checked = filter, onCheckedChange = {
                                                filter = !filter
                                                viewModel.filterProducts("", filter = filter)
                                            })
                                            Text(text = "Filter by favorite")
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Checkbox(checked = darkTheme, onCheckedChange = {
                                                darkTheme = !darkTheme
                                                ThemeState.isLight =!darkTheme
                                            })
                                            Text(text = "Dark theme")
                                        }
                                        Row(verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable {
                                                filter = false
                                                viewModel.filterProducts("")
                                            }) {
                                            Text(
                                                text = "Clear Filter",
                                                Modifier
                                                    .padding(65.dp, 10.dp),
                                                style = TextStyle(color = MaterialTheme.colors.error)
                                            )
                                        }
                                    }

                                }

                            }
                        }
                    }
                }


            }
        })
    }
    AnimatedVisibility(visible = !visible) {
        HomeSearch(searchInput = "", onSearchInputChanged = {
            viewModel.filterProducts(it)
        }, { onBackPress ->
            visible = onBackPress
        })
    }

}
