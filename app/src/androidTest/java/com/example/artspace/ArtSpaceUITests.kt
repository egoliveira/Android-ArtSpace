package com.example.artspace

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import coil.Coil
import coil.EventListener
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.artspace.data.readArtItems
import com.example.artspace.ui.theme.ArtSpaceTheme
import com.example.artspace.ui.view.ArtSpace
import com.example.artspace.vo.Art
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArtSpaceUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val imageRequests = mutableListOf<String>()

    private lateinit var artItems: List<Art>

    @Before
    fun before() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val imageLoader = ImageLoader.Builder(context).eventListener(object : EventListener {
            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                imageRequests.add(request.data.toString())
            }
        }).build()

        Coil.setImageLoader(imageLoader)

        imageRequests.clear()

        composeTestRule.run {
            artItems = readArtItems(context.resources)
        }

        composeTestRule.setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpace(
                        artItems = artItems,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }

    @Test
    fun initial_artwork() {
        composeTestRule.run {
            waitRequests(1)

            with(onNodeWithTag("ArtworkTitle")) {
                assertTextEquals("Mona Lisa")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkAuthor")) {
                assertTextEquals("Leonardo Da Vinci")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkDate")) {
                assertTextEquals("(1503-1519)")
                assertIsDisplayed()
            }

            with(onNodeWithTag("PreviousButton")) {
                assertTextEquals("Previous")
                assertIsNotEnabled()
                assertIsDisplayed()
            }

            with(onNodeWithTag("NextButton")) {
                assertTextEquals("Next")
                assertIsEnabled()
                assertIsDisplayed()
            }

            Assert.assertEquals(1, imageRequests.size)
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[0])
        }
    }

    @Test
    fun artwork_tooltip() {
        composeTestRule.run {
            waitRequests(1)

            with(onNodeWithTag("ArtworkTitle")) {
                assertTextEquals("Mona Lisa")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkAuthor")) {
                assertTextEquals("Leonardo Da Vinci")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkDate")) {
                assertTextEquals("(1503-1519)")
                assertIsDisplayed()
            }

            with(onNodeWithTag("PreviousButton")) {
                assertTextEquals("Previous")
                assertIsNotEnabled()
                assertIsDisplayed()
            }

            with(onNodeWithTag("NextButton")) {
                assertTextEquals("Next")
                assertIsEnabled()
                assertIsDisplayed()
            }

            Assert.assertEquals(1, imageRequests.size)
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[0])

            with(onNodeWithTag("ArtworkWallTooltip")) {
                performTouchInput {
                    longClick()
                }
            }

            with(onNodeWithTag("ArtworkWallTooltipContent")) {
                assertTextEquals("Painted between 1503 and 1517, Da Vinci’s alluring portrait has been dogged by two questions since the day it was made: Who’s the subject and why is she smiling? A number of theories for the former have been proffered over the years: That she’s the wife of the Florentine merchant Francesco di Bartolomeo del Giocondo (ergo, the work’s alternative title, La Gioconda); that she's Leonardo’s mother, Caterina, conjured from Leonardo's boyhood memories of her; and finally, that it's a self-portrait in drag. As for that famous smile, its enigmatic quality has driven people crazy for centuries. Whatever the reason, Mona Lisa’s look of preternatural calm comports with the idealized landscape behind her, which dissolves into the distance through Leonardo’s use of atmospheric perspective.")
                assertIsDisplayed()
            }
        }
    }

    @Test
    fun navigate_to_second_artwork_with_button() {
        composeTestRule.run {
            waitRequests(1)

            onNodeWithTag("NextButton").performClick()

            waitRequests(2)

            with(onNodeWithTag("ArtworkTitle")) {
                assertTextEquals("Girl with a Pearl Earring")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkAuthor")) {
                assertTextEquals("Johannes Vermeer")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkDate")) {
                assertTextEquals("(1665)")
                assertIsDisplayed()
            }

            with(onNodeWithTag("PreviousButton")) {
                assertTextEquals("Previous")
                assertIsEnabled()
                assertIsDisplayed()
            }

            with(onNodeWithTag("NextButton")) {
                assertTextEquals("Next")
                assertIsEnabled()
                assertIsDisplayed()
            }

            Assert.assertEquals(2, imageRequests.size)
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[0])
            Assert.assertEquals(
                "file:///android_asset/girl_with_a_pearl_earring.webp",
                imageRequests[1]
            )
        }
    }

    @Test
    fun navigate_to_second_artwork_with_button_and_go_back() {
        composeTestRule.run {
            waitRequests(1)

            onNodeWithTag("NextButton").performClick()

            waitRequests(2)

            onNodeWithTag("PreviousButton").performClick()

            waitRequests(3)

            with(onNodeWithTag("ArtworkTitle")) {
                assertTextEquals("Mona Lisa")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkAuthor")) {
                assertTextEquals("Leonardo Da Vinci")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkDate")) {
                assertTextEquals("(1503-1519)")
                assertIsDisplayed()
            }

            with(onNodeWithTag("PreviousButton")) {
                assertTextEquals("Previous")
                assertIsNotEnabled()
                assertIsDisplayed()
            }

            with(onNodeWithTag("NextButton")) {
                assertTextEquals("Next")
                assertIsEnabled()
                assertIsDisplayed()
            }

            Assert.assertEquals(3, imageRequests.size)
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[0])
            Assert.assertEquals(
                "file:///android_asset/girl_with_a_pearl_earring.webp",
                imageRequests[1]
            )
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[2])
        }
    }

    @Test
    fun navigate_to_second_artwork_with_swipe() {
        composeTestRule.run {
            waitRequests(1)

            onNodeWithTag("ArtworkWall").performTouchInput {
                swipeLeft()
            }

            waitRequests(2)

            with(onNodeWithTag("ArtworkTitle")) {
                assertTextEquals("Girl with a Pearl Earring")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkAuthor")) {
                assertTextEquals("Johannes Vermeer")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkDate")) {
                assertTextEquals("(1665)")
                assertIsDisplayed()
            }

            with(onNodeWithTag("PreviousButton")) {
                assertTextEquals("Previous")
                assertIsEnabled()
                assertIsDisplayed()
            }

            with(onNodeWithTag("NextButton")) {
                assertTextEquals("Next")
                assertIsEnabled()
                assertIsDisplayed()
            }

            Assert.assertEquals(2, imageRequests.size)
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[0])
            Assert.assertEquals(
                "file:///android_asset/girl_with_a_pearl_earring.webp",
                imageRequests[1]
            )
        }
    }

    @Test
    fun navigate_to_second_artwork_with_swipe_and_go_back() {
        composeTestRule.run {
            waitRequests(1)

            onNodeWithTag("ArtworkWall").performTouchInput {
                swipeLeft()
            }

            waitRequests(2)

            onNodeWithTag("ArtworkWall").performTouchInput {
                swipeRight()
            }

            waitRequests(3)

            with(onNodeWithTag("ArtworkTitle")) {
                assertTextEquals("Mona Lisa")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkAuthor")) {
                assertTextEquals("Leonardo Da Vinci")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkDate")) {
                assertTextEquals("(1503-1519)")
                assertIsDisplayed()
            }

            with(onNodeWithTag("PreviousButton")) {
                assertTextEquals("Previous")
                assertIsNotEnabled()
                assertIsDisplayed()
            }

            with(onNodeWithTag("NextButton")) {
                assertTextEquals("Next")
                assertIsEnabled()
                assertIsDisplayed()
            }

            Assert.assertEquals(3, imageRequests.size)
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[0])
            Assert.assertEquals(
                "file:///android_asset/girl_with_a_pearl_earring.webp",
                imageRequests[1]
            )
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[2])
        }
    }

    @Test
    fun navigate_to_last_artwork_with_button() {
        composeTestRule.run {
            waitRequests(1)

            for (i in 2..10) {
                onNodeWithTag("NextButton").performClick()

                waitRequests(i)
            }

            with(onNodeWithTag("ArtworkTitle")) {
                assertTextEquals("Les Demoiselles d’Avignon")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkAuthor")) {
                assertTextEquals("Pablo Picasso")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkDate")) {
                assertTextEquals("(1907)")
                assertIsDisplayed()
            }

            with(onNodeWithTag("PreviousButton")) {
                assertTextEquals("Previous")
                assertIsEnabled()
                assertIsDisplayed()
            }

            with(onNodeWithTag("NextButton")) {
                assertTextEquals("Next")
                assertIsNotEnabled()
                assertIsDisplayed()
            }

            Assert.assertEquals(10, imageRequests.size)
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[0])
            Assert.assertEquals(
                "file:///android_asset/girl_with_a_pearl_earring.webp",
                imageRequests[1]
            )
            Assert.assertEquals(
                "file:///android_asset/the_starry_night.webp",
                imageRequests[2]
            )
            Assert.assertEquals(
                "file:///android_asset/the_kiss.webp",
                imageRequests[3]
            )
            Assert.assertEquals(
                "file:///android_asset/the_birth_of_venus.webp",
                imageRequests[4]
            )
            Assert.assertEquals(
                "file:///android_asset/arrangement_in_grey_and_black_n_1.webp",
                imageRequests[5]
            )
            Assert.assertEquals(
                "file:///android_asset/the_arnolfini_portrait.webp",
                imageRequests[6]
            )
            Assert.assertEquals(
                "file:///android_asset/the_garden_of_earthly_delights.webp",
                imageRequests[7]
            )
            Assert.assertEquals(
                "file:///android_asset/a_sunday_afternoon_on_the_island_of_la_grande_jatte.webp",
                imageRequests[8]
            )
            Assert.assertEquals(
                "file:///android_asset/les_demoiselles_d_avignon.webp",
                imageRequests[9]
            )
        }
    }

    @Test
    fun navigate_to_last_artwork_with_swipe() {
        composeTestRule.run {
            waitRequests(1)

            for (i in 2..10) {
                onNodeWithTag("ArtworkWall").performTouchInput {
                    swipeLeft()
                }

                waitRequests(i)
            }

            with(onNodeWithTag("ArtworkTitle")) {
                assertTextEquals("Les Demoiselles d’Avignon")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkAuthor")) {
                assertTextEquals("Pablo Picasso")
                assertIsDisplayed()
            }

            with(onNodeWithTag("ArtworkDate")) {
                assertTextEquals("(1907)")
                assertIsDisplayed()
            }

            with(onNodeWithTag("PreviousButton")) {
                assertTextEquals("Previous")
                assertIsEnabled()
                assertIsDisplayed()
            }

            with(onNodeWithTag("NextButton")) {
                assertTextEquals("Next")
                assertIsNotEnabled()
                assertIsDisplayed()
            }

            Assert.assertEquals(10, imageRequests.size)
            Assert.assertEquals("file:///android_asset/mona_lisa.webp", imageRequests[0])
            Assert.assertEquals(
                "file:///android_asset/girl_with_a_pearl_earring.webp",
                imageRequests[1]
            )
            Assert.assertEquals(
                "file:///android_asset/the_starry_night.webp",
                imageRequests[2]
            )
            Assert.assertEquals(
                "file:///android_asset/the_kiss.webp",
                imageRequests[3]
            )
            Assert.assertEquals(
                "file:///android_asset/the_birth_of_venus.webp",
                imageRequests[4]
            )
            Assert.assertEquals(
                "file:///android_asset/arrangement_in_grey_and_black_n_1.webp",
                imageRequests[5]
            )
            Assert.assertEquals(
                "file:///android_asset/the_arnolfini_portrait.webp",
                imageRequests[6]
            )
            Assert.assertEquals(
                "file:///android_asset/the_garden_of_earthly_delights.webp",
                imageRequests[7]
            )
            Assert.assertEquals(
                "file:///android_asset/a_sunday_afternoon_on_the_island_of_la_grande_jatte.webp",
                imageRequests[8]
            )
            Assert.assertEquals(
                "file:///android_asset/les_demoiselles_d_avignon.webp",
                imageRequests[9]
            )
        }
    }

    private fun waitRequests(expected: Int) {
        composeTestRule.waitUntil(
            timeoutMillis = 2000L
        ) {
            imageRequests.size == expected
        }
    }
}