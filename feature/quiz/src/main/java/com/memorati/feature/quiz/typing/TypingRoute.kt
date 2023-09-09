package com.memorati.feature.quiz.typing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.design.component.CardsStackIndexed
import com.memorati.core.design.icon.MemoratiIcons
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.provider.FlashcardsProvider
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.quiz.R

@Composable
fun TypingRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: TypingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TypingScreen(
        modifier = modifier,
        onBack = onBack,
        state = state,
        swipe = viewModel::swipe,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TypingScreen(
    modifier: Modifier = Modifier,
    state: List<Flashcard>,
    onBack: () -> Unit,
    swipe: (Flashcard, Boolean) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            modifier = Modifier.shadow(2.dp),
            title = {
                Text(text = stringResource(id = R.string.typing))
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
            modifier = Modifier.padding(16.dp),
            text = "Typing the matching idiom",
        )

        CardsStackIndexed(
            modifier = Modifier.padding(16.dp),
            items = state,
            onSwipeCardEnd = { false },
            onSwipeCardStart = { false },
            itemKey = { typing -> typing },
        ) { index, card ->
            TypingCard(
                index = index,
                card = card,
                onSwipe = { correct ->
                    swipe(card, correct)
                },
            )
        }
    }
}

@DevicePreviews
@LocalePreviews
@Composable
private fun TypingScreenPreview(
    @PreviewParameter(FlashcardsProvider::class) cards: List<Flashcard>,
) {
    MemoratiTheme {
        Surface {
            TypingScreen(
                state = cards.take(3),
                onBack = {},
                swipe = { _, _ -> },
            )
        }
    }
}
