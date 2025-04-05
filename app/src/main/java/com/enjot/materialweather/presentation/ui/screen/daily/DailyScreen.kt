package com.enjot.materialweather.presentation.ui.screen.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enjot.materialweather.R
import com.enjot.materialweather.presentation.ui.component.banner.conditions.ConditionsBanner
import com.enjot.materialweather.presentation.ui.component.banner.summary.SummaryBanner
import com.enjot.materialweather.presentation.ui.core.ScreenHeader
import com.enjot.materialweather.presentation.utils.toDayOfWeekId
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DailyScreen(
    enterIndex: Int?,
    onNavigateBack: () -> Unit,
    viewModel: DailyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val rowState = rememberLazyListState(
        initialFirstVisibleItemIndex = when (enterIndex) {
            null -> 0
            0 -> 0
            else -> enterIndex - 1
        }
    )
    var index by rememberSaveable {
        mutableIntStateOf(enterIndex ?: 0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .systemBarsPadding()
    ) {
        ScreenHeader(
            header = stringResource(R.string.daily_forecast),
            onArrowBackButtonClick = onNavigateBack
        )
        Spacer(modifier = Modifier.height(16.dp))
        state.daily?.let { dailyList ->
            LazyRow(
                state = rowState
            ) {
                itemsIndexed(dailyList) { i, it ->
                    if (i == 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    SingleDay(
                        name = stringResource(toDayOfWeekId(it.dayOfWeek)),
                        onClick = {
                            index = i
                            scope.launch {
                                when (i) {
                                    0 -> rowState.animateScrollToItem(0)
                                    1 -> rowState.animateScrollToItem(0)
                                    else -> rowState.animateScrollToItem(i - 1)
                                }
                            }
                        },
                        isSelected = index == i
                    )
                    if (i == dailyList.size - 1) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            state.current?.let {
                SummaryBanner(
                    dailyList[index].tempDay,
                    dailyList[index].description
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                ConditionsBanner(daily = dailyList[index])
            }
        }
    }
}

@Composable
fun SingleDay(
    onClick: () -> Unit,
    isSelected: Boolean,
    name: String,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        colors = if (isSelected) {
            CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        } else {
            CardDefaults.cardColors()
        },
        modifier = modifier
            .height(48.dp)
            .width(130.dp)
            .padding(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = name,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}