package com.memorati.core.design.component

import MemoratiIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memorati.core.design.icon.CardMembership

@Composable
fun EmptyScreen(
    imageVector: ImageVector,
    message: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .aspectRatio(ratio = 1f)
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .width(200.dp),
            imageVector = imageVector,
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary.copy(0.5f)),
        )

        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
internal fun EmptyScreenPreview() {
    EmptyScreen(imageVector = MemoratiIcons.CardMembership, message = "Hello")
}
