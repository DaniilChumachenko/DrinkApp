package com.chumachenko.drinkapp.app.ui

import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.drinkapp.app.domain.interactor.AppInteractor

class AppViewModel(
    private val appInteractor: AppInteractor
) : BaseViewModel() {

}