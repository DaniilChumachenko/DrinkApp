package com.chumachenko.info.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chumachenko.core.common.NetworkUtils
import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.storage.DrinksPreferencesManager
import com.chumachenko.info.domain.interactor.InfoInteractor
import kotlinx.coroutines.*

class InfoViewModel(
    private val infoInteractor: InfoInteractor,
    private val networkUtils: NetworkUtils,
    private val preferencesManager: DrinksPreferencesManager,
) : BaseViewModel() {
    lateinit var drink: Drink

    val uiData: LiveData<Pair<Drink, ArrayList<String>>>
        get() = _uiData

    private val _uiData = MutableLiveData<Pair<Drink, ArrayList<String>>>()

    private val searchErrorHandler = CoroutineExceptionHandler { _, exception ->
        handleBaseCoroutineException(exception)
    }

    fun getDrinkById(drinkId: String){
        uiScope.launch {
            val drinkInfo = infoInteractor.getDrinkById(drinkId)
            _uiData.value = Pair(drinkInfo, collectIngredients(drinkInfo))
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
}