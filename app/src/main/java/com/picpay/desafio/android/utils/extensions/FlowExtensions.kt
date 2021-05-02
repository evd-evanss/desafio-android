package com.picpay.desafio.android.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.startCollect(
    onSuccess: (suspend (t: T) -> Unit)? = null,
    onError: ((e: Throwable) -> Unit)? = null,
    onLoading: ((loading: Boolean) -> Unit)? = null,
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) = scope.launch {
    onLoading?.invoke(true)
    try {
        collect {
            onSuccess?.invoke(it)
            onLoading?.invoke(false)
        }
    } catch (e: Throwable) {
        onError?.invoke(e)
        onLoading?.invoke(false)
    }
}

