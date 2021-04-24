package com.picpay.desafio.android.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.startCollect(
    onSuccess: (suspend (t: T) -> Unit)? = null,
    onError: ((e: Throwable) -> Unit)? = null,
    onLoading: ((loading: Boolean) -> Unit)? = null,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    coroutineScope.launch {
        CoroutineScope(Main).launch {
            onLoading?.let { loading ->
                loading(true)
            }
        }
        try {
            collect { result ->
                onSuccess?.let {
                    CoroutineScope(Main).launch {
                        it(result)
                        onLoading?.let { loading ->
                            loading(false)
                        }
                    }
                }
            }
        } catch (e: Throwable) {
            onError?.let {
                CoroutineScope(Main).launch {
                    onError(e)
                }
                onLoading?.let { loading ->
                    loading(false)
                }
            }
        }
    }
}
