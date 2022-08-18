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

    fun getDrinkById(drink: Drink) {
        (uiScope + errorHandler).launch {
            _uiData.value = InfoCell.Shimmer
            if (networkUtils.isOnline()) {
                val drinkInfo = if (drink.strInstructions == "") {
                    infoInteractor.getDrinkById(drink.idDrink)
                } else {
                    drink
                }
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

    private suspend fun collectIngredients(it: Drink): ArrayList<String> {
        val list = arrayListOf<String>().apply {
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
            if (it.strIngredient7 != "")
                add(it.strIngredient7)
            if (it.strIngredient8 != "")
                add(it.strIngredient8)
            if (it.strIngredient9 != "")
                add(it.strIngredient9)
            if (it.strIngredient10 != "")
                add(it.strIngredient10)
            if (it.strIngredient11 != "")
                add(it.strIngredient11)
            if (it.strIngredient12 != "")
                add(it.strIngredient12)
            if (it.strIngredient13 != "")
                add(it.strIngredient13)
            if (it.strIngredient13 != "")
                add(it.strIngredient13)
            if (it.strIngredient14 != "")
                add(it.strIngredient14)
            if (it.strIngredient15 != "")
                add(it.strIngredient15)
        }
        list.forEach {
            infoInteractor.saveIngredient(it.lowercase())
        }
        return list
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