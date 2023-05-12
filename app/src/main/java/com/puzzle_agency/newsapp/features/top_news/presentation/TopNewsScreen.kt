package com.puzzle_agency.newsapp.features.top_news.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.puzzle_agency.newsapp.R
import com.puzzle_agency.newsapp.features.destinations.ArticleDetailsScreenDestination
import com.puzzle_agency.newsapp.features.news_shared.data.model.Article
import com.puzzle_agency.newsapp.ui.composables.navigation_bars.BottomNavigationBar
import com.puzzle_agency.newsapp.ui.theme.BasicBlack
import com.puzzle_agency.newsapp.ui.theme.Brand600
import com.puzzle_agency.newsapp.ui.theme.Brand800
import com.puzzle_agency.newsapp.ui.theme.Dimens
import com.puzzle_agency.newsapp.ui.theme.Neutral300
import com.puzzle_agency.newsapp.ui.theme.Neutral500
import com.puzzle_agency.newsapp.ui.theme.Teal400
import com.puzzle_agency.newsapp.ui.theme.Typography
import com.puzzle_agency.newsapp.ui.theme.White200
import com.puzzle_agency.newsapp.ui.theme.body1Style
import com.puzzle_agency.newsapp.ui.theme.body2Style
import com.puzzle_agency.newsapp.ui.theme.heading2Style
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Destination
@RootNavGraph(start = true)
@Composable
fun TopNewsScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    viewModel: TopNewsViewModel = hiltViewModel()
) {

    val navigateToArticleDetails: (Article) -> Unit = {
        navigator.navigate(ArticleDetailsScreenDestination(it))
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    val lazyColumnListState = rememberLazyListState()

    val shouldStartPaginate by remember {
        derivedStateOf {
            val itemsBeforePagination = 3
            val lastVisibleItemIndex =
                lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            val shouldPaginate =
                lastVisibleItemIndex >= (TopNewsViewModel.PAGE_SIZE * viewModel.currentPage - itemsBeforePagination)

            viewState.canPaginate && shouldPaginate
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate) {
        if (shouldStartPaginate) viewModel.loadNewPage()
    }

    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController = navController, navigator = navigator)
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .background(White200),
            state = lazyColumnListState
        ) {
            item {
                Spacer(modifier = Modifier.height(Dimens.spacing_double_semi))

                Text(
                    text = stringResource(id = R.string.news),
                    style = Typography.heading2Style,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            item {
                NewsPager(viewState.topArticles) { navigateToArticleDetails(it) }
            }

            item {
                Text(
                    text = stringResource(id = R.string.popular_news).uppercase(),
                    style = Typography.body1Style,
                    color = Brand800,
                    modifier = Modifier.padding(
                        horizontal = Dimens.spacing_double,
                        vertical = Dimens.spacing_normal
                    )
                )
            }

            items(
                count = viewState.articles.size,
            ) {
                val article = viewState.articles[it]

                ArticleListItem(article = article) { navigateToArticleDetails(article) }

                val isLastItem = it == viewState.articles.size - 1

                if (isLastItem) {
                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
                } else {
                    Divider(
                        modifier = Modifier
                            .padding(
                                vertical = Dimens.spacing_normal,
                                horizontal = Dimens.spacing_double
                            ),
                        thickness = 1.dp,
                        color = Brand600
                    )
                }
            }

            if (viewState.loading) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(bottom = paddingValues.calculateBottomPadding())
                            .fillMaxWidth()
                            .padding(vertical = Dimens.spacing_normal)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Teal400
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            PullRefreshIndicator(
                refreshing, pullRefreshState, modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsPager(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            pageCount = articles.size,
            modifier = Modifier
                .padding(vertical = Dimens.spacing_normal)
                .fillMaxWidth(),
            state = pagerState
        ) { index ->
            val article = articles[index]
            NewsPagerItem(article) { onArticleClick(article) }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            articles.forEachIndexed { index, _ ->
                val selected = index == pagerState.currentPage
                val color = if (selected) Brand600 else Neutral300

                Box(
                    modifier = Modifier
                        .padding(horizontal = Dimens.spacing_basic_semi)
                        .size(8.dp)
                        .background(color, shape = CircleShape)
                )
            }
        }
    }
}

@Composable
fun NewsPagerItem(
    article: Article,
    onArticleClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)

    Box(
        modifier = Modifier
            .padding(horizontal = Dimens.spacing_double)
            .fillMaxWidth()
            .height(164.dp)
            .clip(shape)
            .shadow(2.dp, shape = shape)
            .background(Color.White, shape = shape)
            .clickable { onArticleClick() },
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = article.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
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
            text = article.title ?: "",
            style = Typography.heading2Style,
            color = White200,
            modifier = Modifier
                .padding(horizontal = Dimens.spacing_normal, vertical = Dimens.spacing_semi)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}


@Composable
fun ArticleListItem(
    article: Article,
    onArticleClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onArticleClick() }
            .padding(horizontal = Dimens.spacing_double),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = article.title ?: "",
                style = Typography.heading2Style,
                color = BasicBlack
            )

            Spacer(modifier = Modifier.height(Dimens.spacing_semi))

            if (article.author != null) {
                Text(text = article.author, style = Typography.body2Style, color = Neutral500)
            }
        }

        Spacer(modifier = Modifier.width(Dimens.spacing_normal))

        val imageShape = RoundedCornerShape(8.dp)
        AsyncImage(
            model = article.urlToImage, contentDescription = article.title,
            modifier = Modifier
                .size(80.dp)
                .clip(imageShape)
                .background(Color.White, shape = imageShape),
            contentScale = ContentScale.Crop
        )
    }
}