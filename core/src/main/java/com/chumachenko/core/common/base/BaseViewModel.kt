package com.chumachenko.core.common.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chumachenko.core.R
import com.chumachenko.core.common.ResourceManager
import com.chumachenko.core.extensions.isInternetError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.koin.android.BuildConfig

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    val showProgress = MutableLiveData<Boolean>()

    private val job = SupervisorJob()
    protected val uiScope = CoroutineScope(Dispatchers.Main.immediate + job)

    override fun onCleared() {
        super.onCleared()
        uiScope.coroutineContext.cancelChildren()
    }

    protected fun handleBaseCoroutineException(
        liveData: com.chumachenko.core.common.SingleEventLiveData<String>,
        exception: Throwable,
        resourceManager: ResourceManager
    ) {
        liveData.value = if (exception.isInternetError()) {
            resourceManager.getString(R.string.error_slow_or_no_internet_connection)
        } else {
            resourceManager.getString(R.string.error_something_went_wrong)
        }

        if (BuildConfig.DEBUG) {
            exception.printStackTrace()
        }
    }

}