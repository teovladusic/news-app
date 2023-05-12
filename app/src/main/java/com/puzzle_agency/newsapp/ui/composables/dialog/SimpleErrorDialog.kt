package com.puzzle_agency.newsapp.ui.composables.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.puzzle_agency.newsapp.R
import com.puzzle_agency.newsapp.ui.theme.Black700
import com.puzzle_agency.newsapp.ui.theme.Dimens
import com.puzzle_agency.newsapp.ui.theme.Teal400
import com.puzzle_agency.newsapp.ui.theme.Typography
import com.puzzle_agency.newsapp.ui.theme.body1Style
import com.puzzle_agency.newsapp.ui.theme.bodyStyle
import com.puzzle_agency.newsapp.ui.theme.heading2Style

@Composable
fun SimpleErrorDialog(title: String, text: String, onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            androidx.compose.material3.Text(
                text = title,
                style = Typography.bodyStyle,
                color = Black700
            )
        },
        text = {
            androidx.compose.material3.Text(
                text = text,
                style = Typography.heading2Style,
                color = Black700
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.padding(
                    bottom = Dimens.spacing_normal,
                    end = Dimens.spacing_normal
                )
            ) {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.ok).uppercase(),
                    style = Typography.body1Style,
                    color = Teal400
                )
            }
        }
    )
}