package com.memorati.feature.quiz.matching

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.quiz.matching.model.Match

@Composable
fun MatchingRoute(
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
) {
    val pairs by viewModel.state.collectAsStateWithLifecycle()
    MatchingScreen(
        modifier = modifier,
        pairs = pairs,
        onSelect = viewModel::onSelect,
    )
}

@Composable
internal fun MatchingScreen(
    modifier: Modifier = Modifier,
    pairs: List<Pair<Match, Match>>,
    onSelect: (Match) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        Text(
            text = "Select the matches",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = modifier.height(16.dp))
        LazyColumn {
            items(pairs) { (idiomMatch, meaningMatch) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    MatchingCard(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        match = idiomMatch,
                        onClick = { onSelect(idiomMatch) },
                        borderColor = borderColor(idiomMatch),
                    )
                    MatchingCard(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        match = meaningMatch,
                        onClick = { onSelect(meaningMatch) },
                        borderColor = borderColor(meaningMatch),
                    )
                }
            }
        }
    }
}

@Composable
private fun borderColor(match: Match): Color = when {
    !match.enabled -> MaterialTheme.colorScheme.surfaceDim
    match.selected -> MaterialTheme.colorScheme.primary
    else -> Color.Transparent
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatchingCard(
    modifier: Modifier = Modifier,
    match: Match,
    onClick: () -> Unit,
    borderColor: Color = Color.Transparent,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(1.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        enabled = match.enabled,
        border = BorderStroke(
            width = 2.dp,
            color = borderColor,
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.Center),
                text = match.title,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@DevicePreviews
@Composable
private fun MatchingScreenPreview() {
    MemoratiTheme {
        Surface {
            MatchingScreen(
                pairs = listOf(
                    Match(
                        id = 0,
                        title = "A",
                        type = Match.Type.IDIOM,
                        selected = true,
                    ) to Match(
                        id = 0,
                        title = "a",
                        type = Match.Type.MEANING,
                    ),
                    Match(
                        id = 1,
                        title = "B",
                        type = Match.Type.IDIOM,
                    ) to Match(
                        id = 1,
                        title = "b",
                        type = Match.Type.MEANING,
                        enabled = false,
                    ),
                    Match(
                        id = 2,
                        title = "C",
                        type = Match.Type.IDIOM,
                    ) to Match(
                        id = 2,
                        title = "c",
                        type = Match.Type.MEANING,
                    ),
                ),
                onSelect = {},
            )
        }
    }
}
