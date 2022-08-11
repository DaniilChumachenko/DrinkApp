package com.chumachenko.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.domain.interactor.CoreInteractor
import kotlinx.coroutines.launch

class CoreViewModel(
    private val coreInteractor: CoreInteractor
) : BaseViewModel() {

    val uiData: LiveData<List<СoreCell>>
        get() = _uiData
    private val _uiData = MutableLiveData<List<СoreCell>>()

    fun getData() {
        uiScope.launch {
            val list = arrayListOf<СoreCell>()
            list.add(СoreCell.Search)
            list.add(СoreCell.Skeleton)
            list.add(СoreCell.Skeleton)
            list.add(СoreCell.Skeleton)
            list.add(СoreCell.Skeleton)
            _uiData.value = list
            getDataFromApi()
        }
    }

    fun getDataFromApi() {
        uiScope.launch {
            val list = arrayListOf<СoreCell>()
            val drinks = coreInteractor.lastChooseDrinks()
            list.clear()
            list.add(СoreCell.Search)
            if (drinks.drinks.isEmpty()) {
                list.add(СoreCell.Empty)
            } else {
                drinks.drinks.forEach {
                    list.add(СoreCell.Item(it, ingredients = collectIngredients(it)))
                }
            }
            _uiData.value = list
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

    fun setupOnCreateMethod() {
        getData()
    }


}