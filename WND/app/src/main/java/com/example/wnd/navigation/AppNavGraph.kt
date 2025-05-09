package com.example.wnd.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wnd.di.AppContainer
import com.example.wnd.ui.dictionary.DictionaryScreen
import com.example.wnd.ui.dictionary.DictionaryViewModel
import com.example.wnd.ui.news.DetailNewsScreen
import com.example.wnd.ui.news.NewsScreen
import com.example.wnd.ui.news.NewsViewModel
import com.example.wnd.ui.weather.WeatherScreen
import com.example.wnd.ui.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    appContainer: AppContainer
) {
    NavHost(
        navController = navController,
        startDestination = Routes.weatherScreen
    ) {
        composable(Routes.weatherScreen) {
            val viewModel: WeatherViewModel = viewModel(
                factory = appContainer.viewModelFactory
            )
            WeatherScreen(viewModel)
        }
        composable(Routes.newsScreen) {
            val viewModel: NewsViewModel = viewModel(
                factory = appContainer.viewModelFactory
            )

                NewsScreen(
                    viewModel = viewModel,
                    modifier = Modifier,
                    navController = navController
                )
        }
        composable(
            route = Routes.detailNewsScreen,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""

                DetailNewsScreen(
                    url = url,
                    modifier = Modifier
                )
        }
        composable(Routes.dictionaryScreen) {
            val viewModel: DictionaryViewModel = viewModel(factory = appContainer.viewModelFactory)
            DictionaryScreen(viewModel)
        }
    }
}