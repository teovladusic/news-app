package com.puzzle_agency.newsapp.ui.composables.navigation_bars

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.puzzle_agency.newsapp.features.NavGraphs
import com.puzzle_agency.newsapp.features.destinations.TopNewsScreenDestination
import com.puzzle_agency.newsapp.ui.composables.BottomNavItem
import com.puzzle_agency.newsapp.ui.theme.Dimens
import com.puzzle_agency.newsapp.ui.theme.Neutral400
import com.puzzle_agency.newsapp.ui.theme.Neutral500
import com.puzzle_agency.newsapp.ui.theme.Typography
import com.puzzle_agency.newsapp.ui.theme.body1Style
import com.puzzle_agency.newsapp.ui.theme.body2Style
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun BottomNavigationBar(
    navController: NavController,
    navigator: DestinationsNavigator,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mainScreenRoute = BottomNavItem.TopNews.screenRoute

    val items = listOf(
        BottomNavItem.TopNews,
        BottomNavItem.AllNews
    )

    val onNavItemClick: (item: BottomNavItem) -> Unit = { item ->
        run {
            if (currentRoute == item.screenRoute.route) return@run

            if (item.screenRoute.route == mainScreenRoute.route) {
                navigator.navigate(
                    item.screenRoute.route,
                    builder = {
                        popUpTo(
                            NavGraphs.root.route,
                            popUpToBuilder = { inclusive = true }
                        )
                    }
                )
                return@run
            }

            navigator.navigate(
                item.screenRoute.route,
                builder = {
                    popUpTo(
                        TopNewsScreenDestination.route,
                    )
                }
            )
        }
    }

    val shape = RoundedCornerShape(12.dp)

    BottomAppBar(
        modifier = Modifier
            .padding(vertical = Dimens.spacing_double_semi, horizontal = Dimens.spacing_double)
            .height(62.dp)
            .fillMaxWidth()
            .background(Color.White, shape = shape)
            .shadow(6.dp, shape = shape),
        containerColor = Color.White,
        contentColor = Neutral400,
        contentPadding = PaddingValues(0.dp),
        actions = {
            items.forEach { item ->
                val selected = currentRoute == item.screenRoute.route

                val backgroundColor = if (selected) item.selectedColor.copy(alpha = 0.2f)
                else Color.White

                IconButton(
                    onClick = { onNavItemClick(item) },
                    modifier = Modifier
                        .padding(horizontal = Dimens.spacing_double_semi)
                        .weight(1f)
                        .background(backgroundColor, shape = RoundedCornerShape(8.dp)),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = stringResource(id = item.titleRes),
                            tint = item.selectedColor,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.height(Dimens.spacing_basic_semi))

                        Text(
                            text = stringResource(id = item.titleRes),
                            style = Typography.body1Style,
                            color = item.selectedColor
                        )
                    }
                }
            }
        },
    )
}