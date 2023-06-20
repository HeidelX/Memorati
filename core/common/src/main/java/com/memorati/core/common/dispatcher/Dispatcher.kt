package com.memorati.core.common.dispatcher

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val memoratiDispatchers: MemoratiDispatchers)

enum class MemoratiDispatchers {
    Default,
    IO,
}
