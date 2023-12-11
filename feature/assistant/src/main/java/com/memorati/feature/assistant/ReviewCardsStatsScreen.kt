package com.memorati.feature.assistant

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.theme.AndroidGreen
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.assistant.state.ReviewCardsStats

@Composable
fun ReviewCardsStatsScreen(
    modifier: Modifier = Modifier,
    state: ReviewCardsStats,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.shapes.medium,
            )
            .padding(32.dp),
    ) {
        Text(
            text = stringResource(R.string.cumulative_result),
            style = MaterialTheme.typography.titleLarge,
        )

        HorizontalDivider(
            modifier = Modifier
                .clip(CircleShape)
                .padding(vertical = 16.dp),
        )

        AccuracyTile(
            modifier = Modifier.padding(bottom = 16.dp),
            labelRes = R.string.reiterated_accuracy,
            accuracy = state.reiteratedAccuracyProgress,
            color = AndroidGreen,
        )

        AccuracyTile(
            modifier = Modifier.padding(bottom = 16.dp),
            labelRes = R.string.solo_accuracy,
            accuracy = state.soloAccuracyProgress,
            color = Color.Yellow,
        )

        AccuracyTile(
            modifier = Modifier.padding(bottom = 16.dp),
            labelRes = R.string.zero_accuracy,
            accuracy = state.zeroAccuracyProgress,
            color = Color.Black,
        )

        Surface(
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(MaterialTheme.shapes.medium),
            shadowElevation = 1.dp,
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(R.string.cards_available_message),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun AccuracyTile(
    modifier: Modifier = Modifier,
    @StringRes labelRes: Int,
    accuracy: Float,
    color: Color,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(labelRes, accuracy.times(100)),
            style = MaterialTheme.typography.bodyMedium,
        )

        LinearProgressIndicator(
            progress = { accuracy },
            modifier = Modifier.fillMaxWidth(),
            color = color,
            strokeCap = StrokeCap.Round,
        )
    }
}

@Composable
@DevicePreviews
@LocalePreviews
fun ReviewResultScreenPreview() {
    MemoratiTheme {
        Surface {
            ReviewCardsStatsScreen(
                state = ReviewCardsStats(
                    totalCount = 100,
                    reiteratedAccuracy = 45,
                    zeroAccuracy = 25,
                    soloAccuracy = 30,
                ),
            )
        }
    }
}
