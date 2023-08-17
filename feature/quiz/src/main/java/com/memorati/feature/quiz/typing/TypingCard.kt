package com.memorati.feature.quiz.typing

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.provider.FlashcardProvider
import com.memorati.core.ui.theme.AndroidGreen
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.quiz.typing.model.Typing

@Composable
internal fun TypingCard(
    modifier: Modifier = Modifier,
    index: Int,
    typing: Typing,
    onTyping: (String) -> Unit,
    onNext: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    val textState by remember {
        mutableStateOf(
            TextFieldValue(
                text = typing.idiom,
                selection = TextRange(typing.idiom.length),
            ),
        )
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        border = BorderStroke(
            width = (typing.percentage * 2).dp,
            brush = Brush.verticalGradient(
                colorStops = borderColors(typing),
            ),
        ),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            text = typing.card.meaning,
            style = MaterialTheme.typography.titleMedium,
        )

        TextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = textState,
            label = {
                Text(text = "Idiom")
            },
            onValueChange = { textField -> onTyping(textField.text) },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { onNext() }),
            maxLines = 1,
        )
    }

    if (index == 2) {
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
}

@Composable
fun borderColors(typing: Typing) = when (typing.state) {
    Typing.State.DEFAULT -> arrayOf(
        typing.percentage to MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        1f to MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    )

    Typing.State.CORRECT -> arrayOf(
        typing.percentage to AndroidGreen.copy(alpha = typing.percentage),
        1f to MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    )

    Typing.State.WRONG -> arrayOf(
        typing.percentage to MaterialTheme.colorScheme.error.copy(alpha = typing.percentage),
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
                typing = Typing(card = flashcard),
                onTyping = { },
                onNext = {},
                index = 0,
            )
        }
    }
}
