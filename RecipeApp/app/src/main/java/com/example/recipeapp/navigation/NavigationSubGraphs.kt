package com.example.recipeapp.navigation

import com.example.feature.search.ui.navigation.SearchFeatureApi
import com.example.media_player.navigation.MediaPlayerFeatureAPi

data class NavigationSubGraphs(
    val searchFeatureApi: SearchFeatureApi,
    val mediaPlayerApi: MediaPlayerFeatureAPi
)