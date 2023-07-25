package com.memorati.feature.creation

import MemoratiIcons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memorati.core.ui.DevicePreviews
import com.memorati.core.ui.LocalePreviews
import com.memorati.core.ui.theme.MemoratiTheme
import com.memorati.feature.creation.model.CreationState

@Composable
internal fun CardCreationRoute(
    modifier: Modifier = Modifier,
    viewModel: CardCreationViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CardCreationScreen(
        modifier = modifier,
        state = state,
        onBack = onBack,
        onSave = viewModel::save,
        onIdiomChange = viewModel::onIdiomChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onIdiomLanguageChange = viewModel::setIdiomLanguageTag,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CardCreationScreen(
    modifier: Modifier = Modifier,
    state: CreationState,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onIdiomChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onIdiomLanguageChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var languagesMenuExpanded by remember { mutableStateOf(false) }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = if (!state.editMode) {
                                R.string.create_new_idiom
                            } else {
                                R.string.update_idiom
                            },
                        ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = MemoratiIcons.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                AutoCompleteTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1.0f)
                        .focusRequester(focusRequester),
                    text = state.idiom,
                    suggestions = state.suggestions,
                    disableSuggestions = state.editMode,
                    label = {
                        Text(text = stringResource(id = R.string.idiom))
                    },
                    onValueChange = onIdiomChange,
                    onSuggestionSelected = onIdiomChange,
                )

                Box(modifier = Modifier.align(Alignment.Top)) {
                    TextButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = {
                            languagesMenuExpanded = !languagesMenuExpanded
                        },
                    ) {
                        Text(text = state.selectedLanguage?.uppercase().orEmpty())

                        Icon(
                            imageVector = MemoratiIcons.ArrowDown,
                            contentDescription = stringResource(R.string.choose_idiom_language),
                        )
                    }

                    DropdownMenu(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .heightIn(max = 200.dp),
                        expanded = languagesMenuExpanded,
                        onDismissRequest = { languagesMenuExpanded = false },
                    ) {
                        state.languages.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(text = language.displayName) },
                                onClick = {
                                    onIdiomLanguageChange(language.tag)
                                    languagesMenuExpanded = false
                                },
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = state.meaning,
                label = {
                    Text(text = stringResource(id = R.string.meaning))
                },
                onValueChange = onDescriptionChange,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                enabled = state.isValid,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.End),
                onClick = {
                    onSave()
                    onBack()
                },
            ) {
                Text(text = stringResource(id = R.string.save))
            }

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}

@DevicePreviews
@LocalePreviews
@Composable
internal fun CardCreationRoutePreview() {
    MemoratiTheme {
        CardCreationScreen(
            state = CreationState(),
            onBack = {},
            onSave = {},
            onIdiomChange = {},
            onDescriptionChange = {},
            onIdiomLanguageChange = {},
        )
    }
}
