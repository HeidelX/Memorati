package com.memorati.feature.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.assistant.state.ReviewResult

@Composable
fun ReviewResultScreen(
    modifier: Modifier = Modifier,
    reviewResult: ReviewResult,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "%.00f%%".format(reviewResult.progress.times(100)),
                fontFamily = FontFamily.Monospace,
                fontSize = 50.sp,
            )

            CircularProgressIndicator(
                modifier = Modifier.size(250.dp),
                progress = reviewResult.progress,
                trackColor = MaterialTheme.colorScheme.primary,
                color = MaterialTheme.colorScheme.inversePrimary,
                strokeWidth = 10.dp,
                strokeCap = StrokeCap.Round,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.result),
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(
                id = R.string.review_result_message,
                reviewResult.correctAnswers,
                reviewResult.wrongAnswers,
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )
    }
}

@Composable
@DevicePreviews
@LocalePreviews
fun ReviewResultScreenPreview() {
    MemoratiTheme {
        Surface {
            ReviewResultScreen(reviewResult = ReviewResult(5, 5))
        }
    }
}
