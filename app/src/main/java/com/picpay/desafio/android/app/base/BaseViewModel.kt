package com.picpay.desafio.android.app.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.utils.livedata.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<Intent, State> : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    protected val _state = SingleLiveEvent<State>()
    val state: LiveData<State> = _state

    abstract fun handle(intent: Intent)

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}