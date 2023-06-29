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
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memorati.feature.assistant.state.ReviewResult

@Composable
fun ReviewResultScreen(
    modifier: Modifier = Modifier,
    reviewResult: ReviewResult,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
            .fillMaxSize(),
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
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.result),
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily.SansSerif,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(
                id = R.string.review_result_message,
                reviewResult.correctAnswers,
                reviewResult.wrongAnswers,
            ),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = FontFamily.SansSerif,
        )
    }
}

@Composable
@Preview
fun ReviewResultScreenPreview() {
    ReviewResultScreen(reviewResult = ReviewResult(5, 5))
}
