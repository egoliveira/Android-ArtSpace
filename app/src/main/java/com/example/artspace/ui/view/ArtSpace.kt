package com.example.artspace.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspace.ui.theme.ArtSpaceTheme
import com.example.artspace.ui.util.isPortrait
import com.example.artspace.vo.Art
import kotlin.math.absoluteValue

@Composable
fun ArtSpace(artItems: List<Art>, modifier: Modifier = Modifier) {
    var currentArt by rememberSaveable {
        val initialArt = if (artItems.isEmpty()) DEFAULT_ART else artItems[0]

        mutableStateOf(initialArt)
    }

    var canGoPrevious by remember {
        val index = artItems.indexOf(currentArt)
        val canGoPrevious = artItems.isNotEmpty() && (index > 0)

        mutableStateOf(canGoPrevious)
    }

    var canGoNext by remember {
        val index = artItems.indexOf(currentArt)
        val canGoNext = artItems.isNotEmpty() && (index < artItems.size - 1)

        mutableStateOf(canGoNext)
    }

    var dragOffset by remember { mutableFloatStateOf(0f) }

    val previousArtworkAction: () -> Unit = {
        val previousIndex = artItems.indexOf(currentArt) - 1

        if (previousIndex >= 0) {
            currentArt = artItems[previousIndex]

            canGoPrevious = previousIndex > 0
            canGoNext = previousIndex < artItems.size - 1
        }
    }

    val nextArtworkAction: () -> Unit = {
        val nextIndex = artItems.indexOf(currentArt) + 1

        if ((nextIndex > 0) && (nextIndex < artItems.size)) {
            currentArt = artItems[nextIndex]

            canGoPrevious = true
            canGoNext = nextIndex < artItems.size - 1
        }
    }

    val handleDragStartEvent: (Offset) -> Unit = {
        dragOffset = 0f
    }

    val handleDragEndEvent: () -> Unit = {
        if (dragOffset.absoluteValue >= SWIPE_THRESHOLD) {
            if (dragOffset > 0) {
                previousArtworkAction.invoke()
            } else {
                nextArtworkAction.invoke()
            }
        }
    }

    val onHorizontalDrag: (PointerInputChange, Float) -> Unit = { _, dragAmount ->
        dragOffset += dragAmount
    }

    if (isPortrait()) {
        ArtSpacePortrait(
            modifier = modifier,
            currentArt = currentArt,
            canGoPrevious = canGoPrevious,
            canGoNext = canGoNext,
            handleDragStartEvent = handleDragStartEvent,
            handleDragEndEvent = handleDragEndEvent,
            onHorizontalDrag = onHorizontalDrag,
            previousArtworkAction = previousArtworkAction,
            nextArtworkAction = nextArtworkAction
        )
    } else {
        ArtSpaceLandscape(
            modifier = modifier,
            currentArt = currentArt,
            canGoPrevious = canGoPrevious,
            canGoNext = canGoNext,
            handleDragStartEvent = handleDragStartEvent,
            handleDragEndEvent = handleDragEndEvent,
            onHorizontalDrag = onHorizontalDrag,
            previousArtworkAction = previousArtworkAction,
            nextArtworkAction = nextArtworkAction
        )
    }
}

private val DEFAULT_ART = Art(
    name = "Mona Lisa",
    author = "Leonardo Da Vinci",
    year = "1503-1519",
    file = "monalisa.webp",
    info = "Mona Lisa"
)

private const val SWIPE_THRESHOLD = 300

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    val artItems = listOf(
        Art(
            name = "Mona Lisa",
            author = "Leonardo Da Vinci",
            year = "1503-1519",
            file = "monalisa.webp",
            info = "Mona Lisa"
        )
    )

    ArtSpaceTheme {
        ArtSpace(artItems)
    }
}