package com.puzzle_agency.newsapp.features.all_news.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.puzzle_agency.newsapp.R
import com.puzzle_agency.newsapp.features.destinations.ArticleDetailsScreenDestination
import com.puzzle_agency.newsapp.features.news_shared.data.model.Article
import com.puzzle_agency.newsapp.ui.composables.dialog.SimpleErrorDialog
import com.puzzle_agency.newsapp.ui.composables.navigation_bars.BottomNavigationBar
import com.puzzle_agency.newsapp.ui.theme.Black700
import com.puzzle_agency.newsapp.ui.theme.Brand600
import com.puzzle_agency.newsapp.ui.theme.Brand800
import com.puzzle_agency.newsapp.ui.theme.Dimens
import com.puzzle_agency.newsapp.ui.theme.Typography
import com.puzzle_agency.newsapp.ui.theme.White200
import com.puzzle_agency.newsapp.ui.theme.bodyStyle
import com.puzzle_agency.newsapp.ui.theme.heading2Style
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AllNewsScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    viewModel: AllNewsViewModel
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(White200),
        bottomBar = {
            BottomNavigationBar(navController = navController, navigator = navigator)
        }
    ) { paddingValues ->

        if (viewState.errorDialogMessage != null) {
            SimpleErrorDialog(
                title = stringResource(id = R.string.error),
                text = viewState.errorDialogMessage ?: ""
            ) {
                viewModel.dismissDialog()
            }
        }

        paddingValues.calculateBottomPadding()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Spacer(modifier = Modifier.height(Dimens.spacing_double_semi))

                Text(
                    text = stringResource(id = R.string.news),
                    style = Typography.heading2Style,
                    color = Color.Black,
                )

                SearchBar(
                    value = viewState.searchValue,
                    onValueChange = { viewModel.onSearchValueChange(it) })

                Box(modifier = Modifier.fillMaxWidth()) {
                    FilledIconButton(
                        onClick = { viewModel.changeSort() },
                        modifier = Modifier
                            .padding(horizontal = Dimens.spacing_double)
                            .align(Alignment.CenterEnd)
                            .height(24.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.White,
                            contentColor = Black700
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = viewState.sort.iconRes),
                            contentDescription = "sort",
                            tint = Black700,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.spacing_double_semi))
            }

            items(count = viewState.articles.size) {
                val article = viewState.articles[it]

                ArticleSearchResult(article = article) {
                    navigator.navigate(
                        ArticleDetailsScreenDestination(article)
                    )
                }

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
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {

    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .padding(horizontal = Dimens.spacing_double, vertical = Dimens.spacing_double_semi)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val shape = RoundedCornerShape(12.dp)
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .requiredHeight(32.dp)
                .weight(1f)
                .height(32.dp)
                .shadow(4.dp, shape = shape)
                .background(color = Color.White, shape = shape),
            singleLine = true,
            textStyle = Typography.bodyStyle.copy(color = Black700),
            decorationBox = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        TextFieldDefaults.TextFieldDecorationBox(
                            value = value,
                            innerTextField = it,
                            enabled = true,
                            singleLine = true,
                            interactionSource = interactionSource,
                            visualTransformation = VisualTransformation.None,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search",
                                    tint = Black700
                                )
                            },
                            placeholder = {
                                androidx.compose.material.Text(
                                    text = stringResource(id = R.string.start_typing),
                                    style = Typography.bodyStyle,
                                    color = Color.Gray
                                )
                            },
                            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                                top = 0.dp,
                                bottom = 0.dp,
                                start = Dimens.spacing_normal,
                                end = Dimens.spacing_normal
                            )
                        )
                    }
                }
            }
        )

        AnimatedVisibility(visible = value.isNotBlank()) {
            TextButton(onClick = { onValueChange("") }) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = Typography.bodyStyle,
                    color = Black700
                )
            }
        }
    }
}

@Composable
fun ArticleSearchResult(
    article: Article,
    onArticleClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onArticleClick() }
            .padding(horizontal = Dimens.spacing_double)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(Color(0x80CBFFFF), shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_all_news),
                contentDescription = "article",
                tint = Brand800,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(Dimens.spacing_normal_semi))

        Text(
            text = article.title ?: "",
            style = Typography.bodyStyle,
            color = Black700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}