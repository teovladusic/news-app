@file:Suppress("UNUSED_EXPRESSION")

package com.puzzle_agency.newsapp.features.all_news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.puzzle_agency.newsapp.ui.composables.navigation_bars.BottomNavigationBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AllNewsScreen(
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