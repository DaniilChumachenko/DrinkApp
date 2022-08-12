package com.chumachenko.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chumachenko.core.R
import com.chumachenko.core.common.NetworkUtils
import com.chumachenko.core.common.ResourceManager
import com.chumachenko.core.common.SingleEventLiveData
import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.DrinksList
import com.chumachenko.core.domain.interactor.CoreInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class CoreViewModel(
    private val coreInteractor: CoreInteractor,
    private val networkUtils: NetworkUtils,
    private val resourceManager: ResourceManager
) : BaseViewModel() {
    var searchQuery: String = ""
        private set
    var animate: Boolean = false
        private set

    val uiData: LiveData<List<CoreCell>>
        get() = _uiData
    val errorSearch: LiveData<String>
        get() = _errorSearch
    val playAnimation: LiveData<Boolean>
        get() = _playAnimation

    private val _uiData = MutableLiveData<List<CoreCell>>()
    private val _errorSearch = SingleEventLiveData<String>()
    private val _playAnimation = MutableLiveData<Boolean>()

    private val queryChannel = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 0)

    private var searchJob: Job? = null

    private val searchErrorHandler = CoroutineExceptionHandler { _, exception ->
        handleBaseCoroutineException(_errorSearch, exception, resourceManager)
        _uiData.value = arrayListOf<CoreCell.Empty>().apply { add(CoreCell.Empty) }
    }

    override fun onCleared() {
        searchJob?.cancel()
        super.onCleared()
    }

    private fun observeQuery() {
        uiScope.launch {
            queryChannel
                .asSharedFlow()
                .debounce(400)
                .collect {
                    handleSuccessGetQuery(it)
                }
        }
    }

    private fun handleSuccessGetQuery(query: String) {
        if (query == searchQuery || query.trim() == searchQuery || query.trim().isEmpty()) {
            return
        }

        searchQuery = query.trim()

        if (searchJob != null && searchJob!!.isActive) {
            searchJob!!.cancel()
        }

        showProgress.value = true

        if (networkUtils.isOnline()) {
            searchJob = (uiScope + searchErrorHandler).launch {
                _playAnimation.value = true
                val drinks = coreInteractor.searchDrinks(searchQuery)
                if (drinks.drinks.isEmpty()) {
                    animate = false
                    _uiData.value = arrayListOf<CoreCell>().apply { add(CoreCell.Empty) }
                } else {
                    animate = true
                    _uiData.value = setShimmers()
                    getDrinksList(drinks)
                    _playAnimation.value = false
                }
                showProgress.value = false
            }
        } else {
            _errorSearch.value =
                resourceManager.getString(R.string.error_slow_or_no_internet_connection)
        }
    }

    private fun setShimmers(): ArrayList<CoreCell> = arrayListOf<CoreCell>().apply {
        add(CoreCell.Space)
        add(CoreCell.Skeleton)
        add(CoreCell.Skeleton)
        add(CoreCell.Skeleton)
        add(CoreCell.Skeleton)
    }

    fun search(query: String) {
        if (query.trim().isEmpty()) {
            searchJob?.cancel()
            searchJob = null

            uiScope.launch { queryChannel.emit("") }

            showProgress.value = false
            searchQuery = ""
        } else {
            uiScope.launch { queryChannel.emit(query) }
        }
    }

    private fun getDrinksList(drinks: DrinksList) {
        uiScope.launch {
            val list = arrayListOf<CoreCell>()
            list.add(CoreCell.Space)
            if (drinks.drinks.isEmpty()) {
                list.add(CoreCell.Empty)
            } else {
                drinks.drinks.forEach {
                    list.add(CoreCell.Item(it, ingredients = collectIngredients(it)))
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

    fun startItem(){
        _uiData.value = arrayListOf<CoreCell>().apply { add(CoreCell.Start) }
    }

    fun setupOnCreateMethod() {
        observeQuery()
        startItem()
        //TODO добавить последний запрос, ресенты
    }
}