package com.memorati.feature.assistant

import MemoratiIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memorati.feature.assistant.state.ReviewResult

@Composable
fun ReviewResultScreen(
    modifier: Modifier = Modifier,
    reviewResult: ReviewResult,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            imageVector = MemoratiIcons.Celebration,
            contentDescription = stringResource(id = R.string.congratulations),
            colorFilter = ColorFilter.lighting(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.inversePrimary,
            ),
        )

        Text(
            text = stringResource(id = R.string.congratulations),
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = stringResource(
                id = R.string.review_result_message,
                reviewResult.correctAnswers,
                reviewResult.wrongAnswers,
            ),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
@Preview
fun ReviewResultScreenPreview() {
    ReviewResultScreen(reviewResult = ReviewResult(10, 5))
}
