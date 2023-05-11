package com.puzzle_agency.newsapp.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.puzzle_agency.newsapp.R
import com.puzzle_agency.newsapp.features.destinations.AllNewsScreenDestination
import com.puzzle_agency.newsapp.features.destinations.Destination
import com.puzzle_agency.newsapp.features.destinations.TopNewsScreenDestination
import com.puzzle_agency.newsapp.ui.theme.Rose400
import com.puzzle_agency.newsapp.ui.theme.Teal400

enum class BottomNavItem(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val screenRoute: Destination,
    val selectedColor: Color
) {
    TopNews(R.string.top_news, R.drawable.ic_home, TopNewsScreenDestination, Teal400),
    AllNews(R.string.all_news, R.drawable.ic_all_news, AllNewsScreenDestination, Rose400)
}
