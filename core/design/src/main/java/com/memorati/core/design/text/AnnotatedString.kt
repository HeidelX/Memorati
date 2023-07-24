package com.memorati.core.design.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun formAnnotatedString(
    query: String,
    text: String,
) = buildAnnotatedString {
    val matches = query.toRegex(RegexOption.IGNORE_CASE).findAll(text)
    val groups = matches.flatMap { it.groups.filterNotNull() }
    append(text)
    groups.forEach { group ->
        addStyle(
            SpanStyle(
                background = MaterialTheme.colorScheme.secondary,
                color = MaterialTheme.colorScheme.onSecondary,
            ),
            group.range.first,
            group.range.last + 1,
        )
    }

    toAnnotatedString()
}
