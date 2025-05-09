package com.example.wnd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wnd.di.AppContainer
import com.example.wnd.navigation.AppNavGraph
import com.example.wnd.ui.theme.WNDTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wnd.navigation.BottomBar
import com.example.wnd.navigation.Routes

class MainActivity : ComponentActivity() {
    private lateinit var appContainer: AppContainer
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Show BottomBar only on main 3 screens
            val showBottomBar = currentRoute in listOf(
                Routes.weatherScreen,
                Routes.newsScreen,
                Routes.dictionaryScreen
            )

            Scaffold(
                topBar = {
                    when (currentRoute) {
                        Routes.newsScreen, Routes.detailNewsScreen, Routes.dictionaryScreen -> {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(text = "News App") // You can change this based on route
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Cyan
                                ),
                                modifier = Modifier.height(56.dp)
                            )
                        }
                    }
                    },
                bottomBar = {
                    if (showBottomBar) {
                        BottomBar(navController)
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AppNavGraph(
                        navController = navController,
                        appContainer = appContainer
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WNDTheme {
        Greeting("Android")
    }
}