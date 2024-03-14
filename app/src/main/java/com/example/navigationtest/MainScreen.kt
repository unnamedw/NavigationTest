package com.example.navigationtest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


const val mainGraphRoute = "main_graph"
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    mainNavController: NavHostController = rememberNavController()
) {
//    val mainNavTracker by remember { mutableStateOf(NavTracker()) }
//    mainNavController.addOnDestinationChangedListener(mainNavTracker)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            navController = mainNavController,
            startDestination = test1Route
        ) {
            composable(route = test1Route) {
                Test1Screen()
            }

            composable(route = test2Route) {
                Test2Screen()
            }

            composable(route = test3Route) {
                Test3Screen(
                    onSignOut = {
//                        mainNavController.graph.clear()
                        mainNavController.popBackStack(test1Route, true)
                        rootNavController.navigate(loginRoute) {
                            popUpTo(loginRoute)
                        }
                    }
                )
            }
        }

        BottomNav(onBottomTabClick = { route ->
            mainNavController.navigate(route) {
                popUpTo(test1Route) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        })
    }


}

@Preview(showBackground = true)
@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    onBottomTabClick: (route: String) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(test1Route) }
    val state by rememberUpdatedState(
        listOf(
            BottomTabState(
                text = "Test1",
                onClick = {
                    onBottomTabClick.invoke(test1Route)
                    selectedTab = test1Route
                },
                selected = selectedTab == test1Route
            ),
            BottomTabState(
                text = "Test2",
                onClick = {
                    onBottomTabClick.invoke(test2Route)
                    selectedTab = test2Route
                },
                selected = selectedTab == test2Route
            ),
            BottomTabState(
                text = "Test3",
                onClick = {
                    onBottomTabClick.invoke(test3Route)
                    selectedTab = test3Route
                },
                selected = selectedTab == test3Route
            ),
        )
    )

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(state) {
            BottomTab(it)
        }
    }
}

data class BottomTabState(
    val text: String = "",
    val onClick: () -> Unit = {},
    val selected: Boolean = false
)

@Composable
fun BottomTab(
    state: BottomTabState = BottomTabState()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { state.onClick.invoke() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = state.text,
            color = if (state.selected) Color.Red else Color.Black
        )
    }
}

const val test1Route = "test1_route"
@Composable
fun Test1Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Test1Screen"
        )
    }
}

const val test2Route = "test2_route"
@Composable
fun Test2Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Test2Screen"
        )
    }
}

const val test3Route = "test3_route"
@Composable
fun Test3Screen(
    onSignOut: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Test3Screen"
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Button(
                onClick = onSignOut
            ) {
                Text(text = "SignOut")
            }
        }
    }
}