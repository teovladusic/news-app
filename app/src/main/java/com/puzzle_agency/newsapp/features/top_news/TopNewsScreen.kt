@file:Suppress("UNUSED_EXPRESSION")

package com.puzzle_agency.newsapp.features.top_news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController
import com.puzzle_agency.newsapp.ui.composables.navigation_bars.BottomNavigationBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@RootNavGraph(start = true)
@Composable
fun TopNewsScreen(
    navController: NavController,
    navigator: DestinationsNavigator
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController = navController, navigator = navigator)
        }
    ) { paddingValues ->

        paddingValues

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}