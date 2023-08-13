package com.memorati.feature.quiz.matching

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.icon.MemoratiIcons
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.quiz.R
import com.memorati.feature.quiz.matching.model.Match

@Composable
fun MatchingRoute(
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val pair by viewModel.state.collectAsStateWithLifecycle()
    MatchingScreen(
        modifier = modifier,
        pair = pair,
        onSelect = viewModel::onSelect,
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MatchingScreen(
    modifier: Modifier = Modifier,
    pair: Pair<List<Match>, List<Match>>,
    onSelect: (Match) -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.matching))
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = MemoratiIcons.ArrowBack,
                        contentDescription = stringResource(id = R.string.return_back),
                    )
                }
            },
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.select_matches_title),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            MatchesColumn(
                modifier = Modifier.weight(1f),
                matches = pair.first,
                onSelect = onSelect,
            )
            MatchesColumn(
                modifier = Modifier.weight(1f),
                matches = pair.second,
                onSelect = onSelect,
            )
        }
    }
}

@Composable
private fun MatchesColumn(
    modifier: Modifier = Modifier,
    matches: List<Match>,
    onSelect: (Match) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(matches) { match ->
            MatchingCard(
                match = match,
                onClick = { onSelect(match) },
                borderColor = borderColor(match),
            )
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
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(1.dp),
        onClick = onClick,
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
                pair = listOf(
                    Match(
                        id = 0,
                        title = "A",
                        type = Match.Type.IDIOM,
                        selected = true,
                    ),
                    Match(
                        id = 0,
                        title = "A",
                        type = Match.Type.IDIOM,
                        selected = false,
                    ),
                ) to listOf(
                    Match(
                        id = 0,
                        title = "a",
                        type = Match.Type.MEANING,
                    ),
                    Match(
                        id = 0,
                        title = "a",
                        type = Match.Type.MEANING,
                    ),
                ),
                onSelect = {},
                onBack = {},
            )
        }
    }
}
