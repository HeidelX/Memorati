package com.memorati.core.design.text

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

fun formAnnotatedString(
    query: String,
    text: String,
) = buildAnnotatedString {
    val matches = query.toRegex(RegexOption.IGNORE_CASE).findAll(text)
    val groups = matches.flatMap { it.groups.filterNotNull() }
    append(text)
    groups.forEach { group ->
        addStyle(
            SpanStyle(fontWeight = FontWeight.SemiBold),
            group.range.first,
            group.range.last + 1,
        )
    }

    toAnnotatedString()
}
