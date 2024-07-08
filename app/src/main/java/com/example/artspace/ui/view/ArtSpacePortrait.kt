package com.example.artspace.ui.view

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.artspace.vo.Art

@Composable
fun ArtSpacePortrait(
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
    Column(modifier = modifier) {
        ArtworkWall(
            art = currentArt,
            modifier = Modifier
                .weight(5f)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = handleDragStartEvent,
                        onDragEnd = handleDragEndEvent,
                        onHorizontalDrag = onHorizontalDrag
                    )
                }
                .testTag("ArtworkWall")
        )

        Spacer(modifier = Modifier.height(8.dp))

        ArtworkInfo(
            art = currentArt,
            modifier = Modifier.weight(1f)
        )

        ArtNavigation(
            canGoPrevious = canGoPrevious,
            canGoNext = canGoNext,
            onPreviousClick = previousArtworkAction,
            onNextClick = nextArtworkAction,
            modifier = Modifier.weight(1f)
        )
    }
}