package com.puzzle_agency.newsapp.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    val spacing_basic_semi: Dp,
    val spacing_basic: Dp,
    val spacing_semi: Dp,
    val spacing_normal_semi: Dp,
    val spacing_normal: Dp,
    val spacing_double_semi: Dp,
    val spacing_double: Dp,
    val spacing_triple_semi: Dp,
    val spacing_triple: Dp,
    val minimum_touch_target: Dp = 48.dp,
)

val normalDimensions = Dimensions(
    spacing_basic_semi = 2.dp,
    spacing_basic = 4.dp,
    spacing_semi = 8.dp,
    spacing_normal_semi = 12.dp,
    spacing_normal = 16.dp,
    spacing_double_semi = 24.dp,
    spacing_double = 32.dp,
    spacing_triple_semi = 40.dp,
    spacing_triple = 48.dp
)
