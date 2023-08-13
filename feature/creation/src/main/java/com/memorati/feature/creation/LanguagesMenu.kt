package com.memorati.feature.creation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.memorati.core.design.icon.MemoratiIcons
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.creation.model.CreationState

@Composable
internal fun LanguagesMenu(
    modifier: Modifier = Modifier,
    state: CreationState,
    onIdiomLanguageChange: (String) -> Unit,
    initialExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(initialExpanded) }

    Box(modifier = modifier) {
        TextButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = { expanded = !expanded },
        ) {
            if (state.selectedLanguage.isNullOrEmpty()) {
                Icon(
                    imageVector = MemoratiIcons.ArrowDown,
                    contentDescription = stringResource(R.string.choose_idiom_language),
                )
            } else {
                Text(
                    text = state.selectedLanguage.uppercase(),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        DropdownMenu(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .heightIn(max = 200.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            state.languages.forEach { language ->
                DropdownMenuItem(
                    text = { Text(text = language.displayName) },
                    onClick = {
                        onIdiomLanguageChange(language.tag)
                        expanded = false
                    },
                )
            }
        }
    }
}

@DevicePreviews
@Composable
internal fun LanguagesMenuPreview() {
    MemoratiTheme {
        Surface {
            LanguagesMenu(
                state = CreationState(),
                onIdiomLanguageChange = {},
            )
        }
    }
}

@DevicePreviews
@LocalePreviews
@Composable
internal fun LanguagesMenuTextPreview() {
    MemoratiTheme {
        Surface {
            LanguagesMenu(
                state = CreationState(
                    selectedLanguage = "de",
                ),
                onIdiomLanguageChange = {},
            )
        }
    }
}
