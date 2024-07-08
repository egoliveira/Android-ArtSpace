package com.example.artspace.ui.view

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.artspace.vo.Art

@Composable
fun ArtSpaceLandscape(
    modifier: Modifier = Modifier,
    currentArt: Art,
    canGoPrevious: Boolean,
    canGoNext: Boolean,
    handleDragStartEvent: (Offset) -> Unit,
    handleDragEndEvent: () -> Unit,
    onHorizontalDrag: (PointerInputChange, Float) -> Unit,
    previousArtworkAction: () -> Unit,
    nextArtworkAction: () -> Unit
) {
    Row(modifier = modifier) {
        ArtworkWall(
            art = currentArt,
            modifier = Modifier
                .weight(2f)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = handleDragStartEvent,
                        onDragEnd = handleDragEndEvent,
                        onHorizontalDrag = onHorizontalDrag
                    )
                }
                .testTag("ArtworkWall")
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            ArtworkInfo(art = currentArt)

            Spacer(modifier = Modifier.height(8.dp))

            ArtNavigation(
                canGoPrevious = canGoPrevious,
                canGoNext = canGoNext,
                onPreviousClick = previousArtworkAction,
                onNextClick = nextArtworkAction
            )
        }
    }
}