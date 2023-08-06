package com.memorati.feature.quiz

import MemoratiIcons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.provider.FlashcardProvider
import com.memorati.core.ui.theme.MemoratiTheme

@Composable
internal fun QuizCard(
    modifier: Modifier = Modifier,
    order: Int,
    flip: Boolean,
    card: Flashcard,
    onFlip: () -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation((order / 2).dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 220.dp)
                .padding(8.dp),
        ) {
            if (flip) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer {
                            rotationY = 180f
                        },
                    text = card.meaning,
                    style = MaterialTheme.typography.headlineMedium,
                )
            } else {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = card.idiom,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            IconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = onFlip,
            ) {
                Icon(imageVector = MemoratiIcons.Flip, contentDescription = "flip")
            }
        }
    }
}

@DevicePreviews
@Composable
internal fun QuizCardPreview(
    @PreviewParameter(FlashcardProvider::class) flashcard: Flashcard,
) {
    MemoratiTheme {
        QuizCard(
            card = flashcard,
            order = 1,
            flip = false,
            onFlip = {},
        )
    }
}

@DevicePreviews
@Composable
internal fun QuizCardFlippedPreview(
    @PreviewParameter(FlashcardProvider::class) flashcard: Flashcard,
) {
    MemoratiTheme {
        QuizCard(
            card = flashcard,
            order = 1,
            flip = true,
            onFlip = {},
        )
    }
}
