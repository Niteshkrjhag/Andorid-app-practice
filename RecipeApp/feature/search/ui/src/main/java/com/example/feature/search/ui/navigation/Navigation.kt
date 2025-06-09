package com.example.feature.search.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.common.navigation.FeatureApi
import com.example.common.navigation.NavigationRoute
import com.example.common.navigation.NavigationSubGraphRoute
import com.example.feature.search.ui.screens.details.RecipeDetails
import com.example.feature.search.ui.screens.details.RecipeDetailsScreen
import com.example.feature.search.ui.screens.details.RecipeDetailsViewModel
import com.example.feature.search.ui.screens.favorite.FavoriteScreen
import com.example.feature.search.ui.screens.favorite.FavoriteViewModel
import com.example.feature.search.ui.screens.recipe_list.RecipeList
import com.example.feature.search.ui.screens.recipe_list.RecipeListScreen
import com.example.feature.search.ui.screens.recipe_list.RecipeListViewModel

interface SearchFeatureApi : FeatureApi

class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.Search.route,
            startDestination = NavigationRoute.RecipeList.route
        ) {

            composable(route = NavigationRoute.RecipeList.route) {
                val viewModel = hiltViewModel<RecipeListViewModel>()
                RecipeListScreen(
                    navHostController = navHostController,
                    viewModel = viewModel
                ){
                        mealId ->
                    viewModel.onEvent(RecipeList.Event.GoToRecipeDetails(mealId))
                }
            }

            composable(route = NavigationRoute.RecipeDetails.route) {
               val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val mealId  = it.arguments?.getString("id")
                LaunchedEffect(key1 = mealId) {
                    mealId?.let{
                        viewModel.onEvent(RecipeDetails.Event.FetchRecipeDetails(mealId))
                    }
                }
                RecipeDetailsScreen(
                    viewModel = viewModel,
                    onNavigationClick = {
                        viewModel.onEvent(RecipeDetails.Event.GoToRecipeListScreen)
                    },
                    onFavoriteClick = {
                        viewModel.onEvent(RecipeDetails.Event.InsertRecipe(it))
                    },
                    onDelete = {
                        viewModel.onEvent(RecipeDetails.Event.DeleteRecipe(it))
                    }, navHostController = navHostController
                )

            }
            composable(NavigationRoute.FavoriteScreen.route) {
                val viewModel = hiltViewModel<FavoriteViewModel>()
                FavoriteScreen(
                    navHostController = navHostController,
                    viewModel = viewModel,
                    onClick = { mealId ->
                        viewModel.onEvent(FavoriteScreen.Event.GoToDetails(mealId))
                    })
            }
        }
    }
}
//
//// ✅ Create a separate composable function to isolate hiltViewModel()
//@Composable
//private fun RecipeListRoute(
//    onRecipeClick: (String) -> Unit
//) {
//    val viewModel: RecipeListViewModel = hiltViewModel()
//
//    RecipeListScreen(
//        viewModel = viewModel,
//        onClick = onRecipeClick
//    )
//}
