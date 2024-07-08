package com.example.artspace.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.R
import com.example.artspace.ui.theme.ArtSpaceTheme

@Composable
fun ArtNavigation(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    canGoPrevious: Boolean,
    canGoNext: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Button(
            modifier = Modifier
                .weight(1f)
                .testTag("PreviousButton"),
            content = { Text(stringResource(id = R.string.art_navigation_previous_button)) },
            enabled = canGoPrevious,
            onClick = onPreviousClick
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            modifier = Modifier
                .weight(1f)
                .testTag("NextButton"),
            content = { Text(stringResource(id = R.string.art_navigation_next_button)) },
            enabled = canGoNext,
            onClick = onNextClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtNavigationPreview() {
    ArtSpaceTheme {
        ArtNavigation(
            canGoPrevious = false,
            canGoNext = true,
            onPreviousClick = {},
            onNextClick = {})
    }
}