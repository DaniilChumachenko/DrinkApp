package com.chumachenko.core.common.notifier

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AppNotifier {

    private val channel = MutableSharedFlow<AppEvent>(replay = 0, extraBufferCapacity = 0)

    val notifier: Flow<AppEvent> = channel.asSharedFlow()

    suspend fun send(event: ShowIngredientsEvent) = channel.emit(event)

    suspend fun send(event: HideBottomSheetEvent) = channel.emit(event)

}