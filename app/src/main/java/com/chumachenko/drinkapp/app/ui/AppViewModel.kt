package com.chumachenko.drinkapp.app.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.drinkapp.app.domain.interactor.AppInteractor
import kotlinx.coroutines.launch

class AppViewModel(
    private val appInteractor: AppInteractor
) : BaseViewModel() {

    val forceUpdate: LiveData<Unit>
        get() = _forceUpdate
    val forceLogout: LiveData<Unit>
        get() = _forceLogout
    val showBookingRefundDialog: LiveData<Double>
        get() = _showBookingRefundDialog

    private val _forceUpdate = com.chumachenko.core.common.SingleEventLiveData<Unit>()
    private val _forceLogout = com.chumachenko.core.common.SingleEventLiveData<Unit>()
    private val _showBookingRefundDialog = com.chumachenko.core.common.SingleEventLiveData<Double>()

    private var lastForceLogoutEventTime = 0L

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun observeEvents() {
        uiScope.launch {
//            appNotifier
//                .notifier
//                .collect { event ->
//                    when (event) {
//                        is ForceUpdateEvent -> {
//                            handleForceUpdateEvent()
//                        }
//                    }
//                }
           val asdasd = appInteractor.updateUserInfo()
            asdasd.drinks
        }
    }

    private fun handleForceUpdateEvent() {
        _forceUpdate.value = Unit
    }

    private fun handleForceLogoutEvent() {
        //debounce logout event
        if (System.currentTimeMillis() - lastForceLogoutEventTime > 5000) {
            lastForceLogoutEventTime = System.currentTimeMillis()
            _forceLogout.value = Unit
        }
    }

    private fun showBookingRefundDialog(amount: Double) {
        _showBookingRefundDialog.value = amount
    }

}