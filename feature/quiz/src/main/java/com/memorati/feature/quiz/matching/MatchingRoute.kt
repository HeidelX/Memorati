package com.memorati.feature.quiz.matching

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardsProvider
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
fun MatchingRoute(
    modifier: Modifier = Modifier,
    viewModel: MatchingViewModel = hiltViewModel(),
) {
    val pairs by viewModel.state.collectAsStateWithLifecycle()
    MatchingScreen(
        modifier = modifier,
        pairs = pairs,
    )
}

@Composable
internal fun MatchingScreen(
    modifier: Modifier = Modifier,
    pairs: List<Pair<Flashcard, Flashcard>>,
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
            items(pairs) { (card, shuffledCard) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    MatchingCard(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        title = card.idiom,
                    )
                    MatchingCard(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        title = shuffledCard.meaning,
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchingCard(
    modifier: Modifier = Modifier,
    title: String,
) {
    Card(
        modifier = modifier
            .padding(1.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.Center),
                text = title,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@DevicePreviews
@Composable
private fun MatchingScreenPreview(
    @PreviewParameter(FlashcardsProvider::class) flashcards: List<Flashcard>,
) {
    MemoratiTheme {
        Surface {
            MatchingScreen(
                pairs = flashcards.zip(flashcards.shuffled()),
            )
        }
    }
}
