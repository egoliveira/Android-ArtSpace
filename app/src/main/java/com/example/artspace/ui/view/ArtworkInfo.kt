package com.example.artspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme
import com.example.artspace.vo.Art

@Composable
fun ArtworkInfo(art: Art, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(4.dp)
        ) {
            Text(
                text = art.name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ArtworkTitle")
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = art.author,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("ArtworkAuthor")
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "(${art.year})",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.testTag("ArtworkDate")
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtworkInfoPreview() {
    val art = Art(
        name = "Mona Lisa",
        author = "Leonardo Da Vinci",
        year = "1503-1519",
        file = "monalisa.webp",
        info = "Mona Lisa"
    )

    ArtSpaceTheme {
        ArtworkInfo(art)
    }
}