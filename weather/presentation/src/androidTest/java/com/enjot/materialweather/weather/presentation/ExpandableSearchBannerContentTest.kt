package com.enjot.materialweather.weather.presentation

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.enjot.materialweather.core.presentation.designsystem.theme.MaterialWeatherTheme
import com.enjot.materialweather.weather.domain.model.SavedLocation
import com.enjot.materialweather.weather.presentation.ui.screen.overview.SearchState
import com.enjot.materialweather.weather.presentation.ui.screen.overview.search.ExpandableSearchBannerContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ExpandableSearchBannerContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun `Correct text displayed depending if there are saved locations or not`() = runTest {

        val savedLocations = MutableStateFlow(emptyList<SavedLocation>())

        composeRule.setContent {
            MaterialWeatherTheme {

                val state by savedLocations.collectAsState(initial = emptyList())

                ExpandableSearchBannerContent(
                    searchState = SearchState.Idle(),
                    onSavedLocationClick = {},
                    onAddToSaved = {},
                    onLocationButtonClick = {},
                    onRemoveFromSaved = {},
                    onSearchResultClick = {},
                    savedLocations = state,
                )
            }
        }

        val savedPlaces = context.resources.getString(R.string.saved_places)
        val noSavedPlaces = context.resources.getString(R.string.no_saved_places)

        composeRule.awaitIdle()

        composeRule.onNodeWithText(savedPlaces).assertDoesNotExist()
        composeRule.onNodeWithText(noSavedPlaces).assertIsDisplayed()

        val savedLocation = savedLocation()

        savedLocations.value = listOf(savedLocation)

        composeRule.onNodeWithText(savedPlaces).assertIsDisplayed()
        composeRule.onNodeWithText(noSavedPlaces).assertDoesNotExist()
    }
}