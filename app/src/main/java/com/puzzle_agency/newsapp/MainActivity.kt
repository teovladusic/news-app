package com.puzzle_agency.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.puzzle_agency.newsapp.features.NavGraphs
import com.puzzle_agency.newsapp.ui.theme.NewsAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}