package com.puzzle_agency.newsapp.features.article_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.puzzle_agency.newsapp.features.common.util.openBrowser
import com.puzzle_agency.newsapp.features.news_shared.data.model.Article
import com.puzzle_agency.newsapp.ui.theme.Black700
import com.puzzle_agency.newsapp.ui.theme.Dimens
import com.puzzle_agency.newsapp.ui.theme.Neutral400
import com.puzzle_agency.newsapp.ui.theme.Typography
import com.puzzle_agency.newsapp.ui.theme.White200
import com.puzzle_agency.newsapp.ui.theme.body1Style
import com.puzzle_agency.newsapp.ui.theme.heading2Style
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ArticleDetailsScreen(
    navigator: DestinationsNavigator,
    article: Article
) {

    val scrollState = rememberScrollState()

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        paddingValues.calculateBottomPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White200)
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                    .graphicsLayer {
                        alpha =
                            1f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.5f)
                        translationY = 0.5f * scrollState.value
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = article.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFF0F172A)
                                )
                            )
                        )
                )

                Text(
                    text = article.title ?: "", style = Typography.heading2Style, color = White200,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 50.dp)
                        .padding(Dimens.spacing_normal)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = LocalConfiguration.current.screenHeightDp.dp)
                    .offset(y = (-50).dp)
                    .background(
                        White200,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
            ) {

                Text(
                    text = article.description ?: "",
                    style = Typography.body1Style,
                    color = Black700,
                    modifier = Modifier.padding(
                        vertical = Dimens.spacing_double,
                        horizontal = Dimens.spacing_normal
                    )
                )
            }
        }

        val context = LocalContext.current

        ArticleDetailsToolbar(
            scrollValue = scrollState.value.toFloat(),
            scrollMaxValue = scrollState.maxValue.toFloat(),
            onShareClick = {
                article.url?.let { context.openBrowser(article.url) }
            }
        ) { navigator.navigateUp() }
    }
}

@Composable
fun ArticleDetailsToolbar(
    scrollValue: Float,
    scrollMaxValue: Float,
    onShareClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Neutral400.copy(alpha = min(1f, (scrollValue / scrollMaxValue) * 5f))),
    ) {
        IconButton(
            onClick = onCloseClick,
            modifier = Modifier
                .padding(horizontal = Dimens.spacing_normal_semi)
                .background(color = Color(0x33FCFCFC), shape = CircleShape)
                .size(32.dp)
                .align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "back",
                tint = Color.White,
                modifier = Modifier.padding(Dimens.spacing_basic_semi)
            )
        }

        IconButton(
            onClick = onShareClick,
            modifier = Modifier
                .padding(horizontal = Dimens.spacing_normal_semi)
                .background(color = Color(0x33FCFCFC), shape = CircleShape)
                .size(32.dp)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "share",
                tint = Color.White,
                modifier = Modifier.padding(Dimens.spacing_basic_semi)
            )
        }
    }


}