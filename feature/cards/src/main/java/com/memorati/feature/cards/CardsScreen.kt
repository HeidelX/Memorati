package com.memorati.feature.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.memorati.core.model.Flashcard
import com.memorati.core.ui.DevicePreviews
import kotlinx.datetime.Clock

@Composable
fun CardsRoute(
    modifier: Modifier = Modifier,
    viewModel: CardsViewModel = hiltViewModel(),
) {
    val cards by viewModel.cards.collectAsState(initial = listOf())
    CardsScreen(cards, modifier)
}

@Composable
internal fun CardsScreen(
    cards: List<Flashcard>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 16.dp
        )

    ) {
        items(cards) { card ->
            CardItem(card)
            Spacer(modifier = modifier.height(10.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CardItem(
    card: Flashcard,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {

        Surface(
            modifier
                .padding(horizontal = 30.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.inversePrimary)
                .fillMaxSize()
                .padding(20.dp),
            color = MaterialTheme.colorScheme.inversePrimary
        ) {
            Text(
                text = card.front,

                )
        }

        Surface(
            modifier
                .padding(bottom = 30.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(30.dp),
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(modifier = modifier) {
                Text(
                    text = card.back,
                    style = MaterialTheme.typography.headlineMedium
                )

                Icon(
                    Icons.Rounded.VolumeUp,
                    contentDescription = "",
                    modifier = modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@DevicePreviews
@Composable
internal fun CardsScreenPreview() {
    CardsScreen(
        cards = listOf(
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
            Flashcard(
                front = "Hello",
                back = "Hallo",
                createdAt = Clock.System.now(),
            ),
        ),
    )
}

@DevicePreviews
@Composable
internal fun CardItemPreview() {
    CardItem(
        card = Flashcard(
            front = "Hello",
            back = "Hallo",
            createdAt = Clock.System.now(),
        ),
    )
}
