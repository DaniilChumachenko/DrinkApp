package com.chumachenko.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.core.data.model.Drink
import com.chumachenko.search.data.domain.interactor.SearchInteractor
import kotlinx.coroutines.launch

class SearchViewModel(
    private val coreInteractor: SearchInteractor
) : BaseViewModel() {

    val uiData: LiveData<List<SearchCell>>
        get() = _uiData
    private val _uiData = MutableLiveData<List<SearchCell>>()

    fun getData() {
        uiScope.launch {
            val list = arrayListOf<SearchCell>()
            list.add(SearchCell.Search)
            list.add(SearchCell.Skeleton)
            list.add(SearchCell.Skeleton)
            list.add(SearchCell.Skeleton)
            list.add(SearchCell.Skeleton)
            _uiData.value = list
            getDataFromApi()
        }
    }

    fun getDataFromApi() {
        uiScope.launch {
            val list = arrayListOf<SearchCell>()
            val drinks = coreInteractor.lastChooseDrinks()
            list.clear()
            list.add(SearchCell.Search)
            if (drinks.drinks.isEmpty()) {
                list.add(SearchCell.Empty)
            } else {
                drinks.drinks.forEach {
                    list.add(SearchCell.Item(it, ingredients = collectIngredients(it)))
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