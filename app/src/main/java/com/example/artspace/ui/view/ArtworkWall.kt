package com.example.artspace.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.artspace.ui.theme.ArtSpaceTheme
import com.example.artspace.vo.Art

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkWall(art: Art, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .shadow(elevation = 2.dp, clip = true)
            .border(border = BorderStroke(width = 2.dp, color = Color.LightGray))
    ) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = {
                PlainTooltip {
                    Text(
                        art.info,
                        modifier = Modifier.testTag("ArtworkWallTooltipContent")
                    )
                }
            },
            state = rememberTooltipState(),
            modifier = Modifier.testTag("ArtworkWallTooltip")
        ) {
            AsyncImage(
                model = art.file,
                contentDescription = art.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("ArtworkImage")
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtworkWallPreview() {
    val art = Art(
        name = "Mona Lisa",
        author = "Leonardo Da Vinci",
        year = "1503-1519",
        file = "monalisa.webp",
        info = "Mona Lisa"
    )

    ArtSpaceTheme {
        ArtworkWall(art)
    }
}