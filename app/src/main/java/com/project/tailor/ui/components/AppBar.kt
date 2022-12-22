package com.project.tailor.ui.components

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun AppTopBar(
    viewModel: ProductViewModel, navIconClick: () -> Unit
) {
    var mDisplayMenu by remember { mutableStateOf(false) }
    var filter by remember { mutableStateOf(false) }
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
                        contentDescription = ""
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
                            contentDescription = ""
                        )
                    }
                    IconButton(onClick = {
                        mDisplayMenu = !mDisplayMenu
                    }) {
                        Box {
                            //filter drop down menu
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = ""
                            )
                            DropdownMenu(
                                expanded = mDisplayMenu,
                                onDismissRequest = { mDisplayMenu = false }) {
                                DropdownMenuItem(onClick = { }) {
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
                                }
//                                Row(
//                                    verticalAlignment = Alignment.CenterVertically,
//                                    modifier = Modifier.clickable {}
//                                ) {
//                                    Checkbox(checked = filter, onCheckedChange = {
//                                        //Todo :change theme
//                                    })
//                                    Text(text = "Dark theme")
//                                }
                                Row(verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable {
                                        filter = false
                                        viewModel.filterProducts("")
                                    }) {
                                    Text(text = "Clear Filter",
                                        Modifier
                                            .padding(65.dp, 0.dp)
                                            )
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
