package com.example.wnd.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.wnd.R

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)

val bottomNavItem = listOf(
    BottomNavItem(route = Routes.weatherScreen, icon = R.drawable.weather, label = "Weather"),
    BottomNavItem(route = Routes.newsScreen, icon = R.drawable.news, label = "News"),
    BottomNavItem(route = Routes.dictionaryScreen, icon = R.drawable.dictionary, label = "Dictionary")
)