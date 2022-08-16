package com.chumachenko.core.common.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    val showProgress = MutableLiveData<Boolean>()

    private val job = SupervisorJob()
    protected val uiScope = CoroutineScope(Dispatchers.Main.immediate + job)

    override fun onCleared() {
        super.onCleared()
        uiScope.coroutineContext.cancelChildren()
    }

    protected fun handleBaseCoroutineException(
        exception: Throwable
    ) = exception.printStackTrace()

}