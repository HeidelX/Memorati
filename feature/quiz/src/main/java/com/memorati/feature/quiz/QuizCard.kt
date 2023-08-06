package com.memorati.feature.quiz

import MemoratiIcons
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    card: Flashcard,
) {
    var flip by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (flip) 180f else 0f,
        label = "Rotation",
        animationSpec = tween(500),
    )
    Card(
        modifier = modifier.graphicsLayer {
            rotationX = rotation
            cameraDistance = 10 * density
        },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(
            width = 1.dp,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 220.dp)
                .padding(8.dp),
        ) {
            if (rotation > 90f) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer {
                            rotationX = 180f
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
                modifier = Modifier.align(
                    if (rotation > 90f) Alignment.TopEnd else Alignment.BottomEnd,
                ),
                onClick = {
                    flip = !flip
                },
            ) {
                Icon(
                    imageVector = MemoratiIcons.Flip,
                    contentDescription = "flip",
                )
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
        )
    }
}
