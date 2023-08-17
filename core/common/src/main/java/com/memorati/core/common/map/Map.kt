package com.memorati.core.common.map

fun <T, R> Map<T, R>.mutate(block: MutableMap<T, R>.() -> Unit) =
    toMutableMap().apply(block).toMap()
