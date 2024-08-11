package com.mimo.presentation.util

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

sealed class ThrottleDuration(
    val period: Long,
) {
    data object SHORT : ThrottleDuration(period = 250L)

    data object DEFAULT : ThrottleDuration(period = 500L)

    data object LONG : ThrottleDuration(period = 1000L)
}

fun View.setClickEvent(
    uiScope: CoroutineScope,
    duration: Long = ThrottleDuration.DEFAULT.period,
    onClick: () -> Unit,
) {
    clicks()
        .throttleFirst(duration)
        .onEach {
            Timber.tag("mini-moment").d("button clicked : ${System.currentTimeMillis()}")
            onClick.invoke()
        }.launchIn(uiScope)
}

fun <T> Flow<T>.throttleFirst(duration: Long = ThrottleDuration.DEFAULT.period): Flow<T> =
    flow {
        var lastEmissionTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastEmissionTime > duration) {
                emit(value)
                lastEmissionTime = currentTime
            }
        }
    }

fun View.clicks(): Flow<Unit> =
    callbackFlow {
        setOnClickListener {
            this.trySend(Unit)
        }
        awaitClose { setOnClickListener(null) }
    }
