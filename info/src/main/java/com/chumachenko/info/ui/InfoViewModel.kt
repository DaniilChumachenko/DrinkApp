package com.chumachenko.info.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chumachenko.core.common.NetworkUtils
import com.chumachenko.core.common.SingleEventLiveData
import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.core.common.notifier.AppNotifier
import com.chumachenko.core.common.notifier.HideBottomSheetEvent
import com.chumachenko.core.common.notifier.ShowIngredientsEvent
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.data.storage.DrinksPreferencesManager
import com.chumachenko.info.domain.interactor.InfoInteractor
import kotlinx.coroutines.*

class InfoViewModel(
    private val infoInteractor: InfoInteractor,
    private val networkUtils: NetworkUtils,
    private val appNotifier: AppNotifier
) : BaseViewModel() {
    lateinit var drink: Drink

    val uiData: LiveData<InfoCell>
        get() = _uiData

    private val _uiData = MutableLiveData<InfoCell>()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        handleBaseCoroutineException(exception)
    }

    fun getDrinkById(drinkId: String) {
        (uiScope + errorHandler).launch {
            _uiData.value = InfoCell.Shimmer
            if (networkUtils.isOnline()) {
                val drinkInfo = infoInteractor.getDrinkById(drinkId)
                _uiData.value = InfoCell.Item(drinkInfo, collectIngredients(drinkInfo))
                saveSearchItem(drinkInfo)
            } else {
                delay(1000)
                showOpenInfoErrorNetwork()
            }
        }
    }

    fun openSearchByIngredients(name: String) {
        uiScope.launch {
            appNotifier.send(ShowIngredientsEvent(name))
        }
    }

    private fun collectIngredients(it: Drink): ArrayList<String> = arrayListOf<String>().apply {
        if (it.strIngredient1 != "")
            add(it.strIngredient1)
        if (it.strIngredient2 != "")
            add(it.strIngredient2)
        if (it.strIngredient3 != "")
            add(it.strIngredient3)
        if (it.strIngredient4 != "")
            add(it.strIngredient4)
        if (it.strIngredient5 != "")
            add(it.strIngredient5)
        if (it.strIngredient6 != "")
            add(it.strIngredient6)
    }

    private fun saveSearchItem(drink: Drink) {
        uiScope.launch {
            try {
                infoInteractor.saveOpenDrink(drink)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showOpenInfoErrorNetwork() {
        uiScope.launch {
            appNotifier.send(HideBottomSheetEvent())
        }
    }
}