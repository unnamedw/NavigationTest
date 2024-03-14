package com.example.navigationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationtest.ui.theme.NavigationTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HolderScreen()
                }
            }
        }
    }
}

@Composable
fun HolderScreen() {
    val rootNavController = rememberNavController()
    val mainNavController = rememberNavController()
//    val rootNavTracker by remember { mutableStateOf(NavTracker()) }
//    rootNavController.addOnDestinationChangedListener(rootNavTracker)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = rootNavController, startDestination = loginRoute) {

            composable(loginRoute) {
                LoginScreen(
                    onSignIn = {
                        rootNavController.navigate(mainGraphRoute) {
                            popUpTo(rootNavController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(mainGraphRoute) {
                MainScreen(
//                    mainNavController = mainNavController,
                    rootNavController = rootNavController
                )
            }

        }
    }
}

const val loginRoute = "login_route"
@Composable
fun LoginScreen(
    onSignIn: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "LoginScreen"
        )
        Box(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = onSignIn
            ) {
                Text(text = "SignIn")
            }
        }
    }
}
