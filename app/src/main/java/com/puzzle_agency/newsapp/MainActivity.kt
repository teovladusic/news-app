package com.puzzle_agency.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.puzzle_agency.newsapp.features.NavGraphs
import com.puzzle_agency.newsapp.features.all_news.presentation.AllNewsViewModel
import com.puzzle_agency.newsapp.features.top_news.presentation.TopNewsViewModel
import com.puzzle_agency.newsapp.ui.theme.NewsAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val allNewsViewModel: AllNewsViewModel by viewModels()
    private val topNewsViewModel: TopNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    modifier = Modifier.fillMaxSize(),
                    dependenciesContainerBuilder = {
                        dependency(this@MainActivity.allNewsViewModel)
                        dependency(this@MainActivity.topNewsViewModel)
                    }
                )
            }
        }
    }
}