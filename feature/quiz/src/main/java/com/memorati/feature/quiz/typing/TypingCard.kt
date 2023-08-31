package com.memorati.feature.quiz.typing

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.provider.FlashcardProvider
import com.memorati.core.ui.theme.AndroidGreen
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.quiz.R

@Composable
internal fun TypingCard(
    modifier: Modifier = Modifier,
    index: Int,
    card: Flashcard,
    onSwipe: (Boolean) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var typedIdiom by rememberSaveable { mutableStateOf("") }
    val percentage by animateFloatAsState(
        targetValue = when {
            typedIdiom.isEmpty() -> 0f
            else -> minOf(1f, typedIdiom.length.toFloat() / card.idiom.length)
        },
        label = "Percentage",
        animationSpec = tween(5_00),
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        ),
        border = BorderStroke(
            width = (percentage * 2).dp,
            brush = Brush.verticalGradient(
                colorStops = borderColors(card, typedIdiom.trim(), percentage),
            ),
        ),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            text = card.meaning,
            style = MaterialTheme.typography.titleMedium,
        )

        TextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = typedIdiom,
            label = { Text(text = stringResource(R.string.idiom)) },
            onValueChange = { text ->
                typedIdiom = text
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { onSwipe(card.idiom.contains(typedIdiom.trim(), true)) },
            ),
            maxLines = 1,
        )
    }

    if (index == 2) {
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
}

@Composable
fun borderColors(card: Flashcard, typedIdiom: String, percentage: Float) = when {
    typedIdiom.isEmpty() -> arrayOf(
        percentage to MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        1f to MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    )

    card.idiom.contains(typedIdiom, true) -> arrayOf(
        percentage to AndroidGreen.copy(alpha = percentage),
        1f to MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    )

    else -> arrayOf(
        percentage to MaterialTheme.colorScheme.error.copy(alpha = percentage),
        1f to MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    )
}

@DevicePreviews
@LocalePreviews
@Composable
private fun TypingCardPreview(
    @PreviewParameter(FlashcardProvider::class) flashcard: Flashcard,
) {
    MemoratiTheme {
        Surface {
            TypingCard(
                card = flashcard,
                index = 0,
                onSwipe = {},
            )
        }
    }
}
