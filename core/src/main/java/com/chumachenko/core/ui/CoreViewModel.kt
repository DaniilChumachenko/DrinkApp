package com.chumachenko.core.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.chumachenko.core.common.NetworkUtils
import com.chumachenko.core.common.SingleEventLiveData
import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.DrinksList
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.data.storage.database.DrinkDatabase
import com.chumachenko.core.domain.interactor.CoreInteractor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce

class CoreViewModel(
    private val coreInteractor: CoreInteractor,
    private val networkUtils: NetworkUtils
) : BaseViewModel() {
    var searchQuery: String = ""
        private set
    var animate: Boolean = false
        private set

    val uiData: LiveData<List<CoreCell>>
        get() = _uiData
    val errorSearch: LiveData<Unit>
        get() = _errorSearch
    val playAnimation: LiveData<Boolean>
        get() = _playAnimation

    private val _uiData = MutableLiveData<List<CoreCell>>()
    private val _errorSearch = SingleEventLiveData<Unit>()
    private val _playAnimation = MutableLiveData<Boolean>()

    private val queryChannel = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 0)

    private var searchJob: Job? = null

    private val searchErrorHandler = CoroutineExceptionHandler { _, exception ->
        handleBaseCoroutineException(exception)
        showEmptyItem()
    }

    fun setupOnCreateMethod() {
        observeQuery()
        setRecentSearchData()
        //TODO добавить последний запрос, ресенты
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
                    showEmptyItem()
                } else {
                    animate = true
                    getDrinksList(drinks)
                    _playAnimation.value = false
                }
                showProgress.value = false
            }
        } else {
            _errorSearch.value = Unit
        }
    }

    private fun getDrinksList(drinks: DrinksList) {
        uiScope.launch {
            if (drinks.drinks.isEmpty()) {
                showEmptyItem()
            } else {
                _uiData.value = arrayListOf<CoreCell>().apply {
                    add(CoreCell.Space)
                    drinks.drinks.forEach {
                        add(CoreCell.Item(it, ingredients = collectIngredients(it)))
                    }
                }
            }
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

    fun setRecentSearchData() {
        uiScope.launch {
            val recent = coreInteractor.getRecentSearches()
            if (recent.isNotEmpty()) {
                _uiData.value = arrayListOf<CoreCell>().apply {
                    add(CoreCell.Space)
                    (recent as ArrayList<SearchResult>).apply {
                        add(0, SearchResult("Recent search: "))
                    }
                    add(CoreCell.Recent(recent))
                    add(CoreCell.Start)
                }
            } else {
                showStartItem()
            }
        }
    }

    fun setShimmers() {
        _uiData.value = arrayListOf<CoreCell>().apply {
            add(CoreCell.Space)
            add(CoreCell.Skeleton)
            add(CoreCell.Skeleton)
            add(CoreCell.Skeleton)
            add(CoreCell.Skeleton)
        }
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

    fun showStartItem() {
        _uiData.value = arrayListOf<CoreCell>().apply { add(CoreCell.Start) }
    }

    fun showEmptyItem() {
        _uiData.value = arrayListOf<CoreCell.Empty>().apply { add(CoreCell.Empty) }
    }

    fun saveSearchItem(item: SearchResult) {
        uiScope.launch {
            try {
                coreInteractor.addRecentSearch(item)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun recentClear() {
        uiScope.launch {
            showStartItem()
            coreInteractor.clearRecentSearch()
        }
    }
}