package com.memorati.feature.creation

import MemoratiIcons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memorati.core.design.text.formAnnotatedString

@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: @Composable () -> Unit,
    suggestions: List<String>,
    onValueChange: (String) -> Unit,
    onSuggestionSelected: (String) -> Unit,
) {
    val view = LocalView.current
    val lazyListState = rememberLazyListState()
    var focused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.heightIn(max = TextFieldDefaults.MinHeight * 4),
    ) {
        val exists = suggestions.any { it.equals(text, ignoreCase = false) }
        TextField(
            modifier = Modifier
                .onFocusChanged {
                    focused = it.isFocused
                }
                .fillMaxWidth(),
            value = text,
            isError = exists,
            supportingText = {
                if (exists) {
                    Text(text = stringResource(R.string.idiom_exists))
                }
            },
            onValueChange = onValueChange,
            label = label,
            trailingIcon = {
                if (focused) {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(
                            imageVector = MemoratiIcons.Close,
                            contentDescription = stringResource(R.string.clear),
                        )
                    }
                }
            },
        )

        AnimatedVisibility(visible = suggestions.any { !it.equals(text, ignoreCase = false) }) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)),
                state = lazyListState,
            ) {
                items(suggestions) { suggestion ->
                    Row(
                        modifier = Modifier
                            .height(TextFieldDefaults.MinHeight)
                            .fillMaxWidth()
                            .clickable {
                                onSuggestionSelected(suggestion)
                                view.clearFocus()
                            },
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = formAnnotatedString(text, suggestion),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AutoCompleteTextFieldPreview() {
    AutoCompleteTextField(
        text = "Idiom",
        suggestions = listOf("Apple", "Google", "Microsoft"),
        onValueChange = {},
        onSuggestionSelected = {},
        label = {},
    )
}
