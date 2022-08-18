package com.chumachenko.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chumachenko.core.R
import com.chumachenko.core.common.NetworkUtils
import com.chumachenko.core.common.ResourceManager
import com.chumachenko.core.common.SingleEventLiveData
import com.chumachenko.core.common.base.BaseViewModel
import com.chumachenko.core.common.notifier.AppNotifier
import com.chumachenko.core.common.notifier.HideBottomSheetEvent
import com.chumachenko.core.common.notifier.ShowIngredientsEvent
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.data.storage.DrinksPreferencesManager
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
    private val preferencesManager: DrinksPreferencesManager,
    private val appNotifier: AppNotifier,
    private val resourceManager: ResourceManager
) : BaseViewModel() {
    var searchQuery: String = ""
        private set
    var clearSearchOn: Boolean = false
    var searchFromInfo: Boolean = false
    val lastQuery: String
        get() {
            return preferencesManager.lastQuery
        }

    val uiData: LiveData<List<CoreCell>>
        get() = _uiData
    val errorSearch: LiveData<Unit>
        get() = _errorSearch
    val updateHint: LiveData<String>
        get() = _updateHint
    val updateInput: LiveData<String>
        get() = _updateInput
    val hideBottomSheet: LiveData<Unit>
        get() = _hideBottomSheet

    private val _uiData = MutableLiveData<List<CoreCell>>()
    private val _errorSearch = SingleEventLiveData<Unit>()
    private val _updateHint = SingleEventLiveData<String>()
    private val _updateInput = SingleEventLiveData<String>()
    private val _hideBottomSheet = SingleEventLiveData<Unit>()

    private val queryChannel = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 0)

    private var searchJob: Job? = null

    private val searchErrorHandler = CoroutineExceptionHandler { _, exception ->
        handleBaseCoroutineException(exception)
        showEmptyItem()
    }

    override fun onCleared() {
        searchJob?.cancel()
        super.onCleared()
    }

    fun setupOnCreateMethod() {
        observeQuery()
        observeEvents()
        setRecentSearchData()
    }

    private fun observeEvents() {
        uiScope.launch {
            appNotifier
                .notifier
                .collect { event ->
                    when (event) {
                        is ShowIngredientsEvent -> {
                            findByIngredients(event.ingredient)
                            searchFromInfo = true
                            _updateInput.value = event.ingredient
                            _hideBottomSheet.value = Unit
                        }
                        is HideBottomSheetEvent -> {
                            _hideBottomSheet.value = Unit
                            _errorSearch.value = Unit
                        }
                    }
                }
        }
    }

    private fun findByIngredients(ingredient: String) {
        uiScope.launch {
            setShimmers()
            val drinks = coreInteractor.filterByIngredients(ingredient)
            if (drinks.drinks.isNotEmpty()) {
                getDrinksList(drinks.drinks)
                searchFromInfo = false
            } else {
                showStartItem()
            }
        }
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

    private suspend fun handleSuccessGetQuery(query: String) {
        if (query == searchQuery || query.trim() == searchQuery || query.trim().isEmpty()) {
            return
        }

        searchQuery = query.trim()

        if (searchJob != null && searchJob!!.isActive) {
            searchJob!!.cancel()
        }

        showProgress.value = true

        val saveIngredient = coreInteractor.getSavedIngredients(query.lowercase())

        if (networkUtils.isOnline()) {
            if (saveIngredient == null) {
                searchJob = (uiScope + searchErrorHandler).launch {
                    setShimmers()
                    val drinks = coreInteractor.searchDrinks(searchQuery)
                    if (drinks.drinks.isEmpty()) {
                        showEmptyItem()
                    } else {
                        getDrinksList(drinks.drinks)
                    }
                    showProgress.value = false
                }
            } else {
                findByIngredients(query)
            }
        } else {
            showEmptyItem()
            _errorSearch.value = Unit
        }
    }

    private fun getDrinksList(drinks: List<Drink>) {
        uiScope.launch {
            if (drinks.isEmpty()) {
                showEmptyItem()
            } else {
                _uiData.value = arrayListOf<CoreCell>().apply {
                    add(CoreCell.Space)
                    drinks.forEach {
                        add(CoreCell.Item(it))
                    }
                }
            }
        }
    }

    fun setRecentSearchData() {
        uiScope.launch {
            val drinks = coreInteractor.getLastDrinks()
            val recent = coreInteractor.getRecentSearches()
            val list = arrayListOf<CoreCell>()
            list.add(CoreCell.Space)
            if (recent.isNotEmpty()) {
                (recent as ArrayList<SearchResult>).apply {
                    add(0, SearchResult("Recent search: "))
                }
                list.add(CoreCell.Recent(recent))
            }
            if (drinks.isNotEmpty()) {
                drinks.forEach {
                    list.add(
                        CoreCell.Item(
                            it.toDrinkModel()
                        )
                    )
                }
            } else {
                list.add(CoreCell.Start)
            }
            _uiData.value = list
        }
    }

    fun setShimmers() {
        _uiData.value = arrayListOf<CoreCell>().apply {
            add(CoreCell.Space)
            add(CoreCell.Shimmer)
            add(CoreCell.Shimmer)
            add(CoreCell.Shimmer)
            add(CoreCell.Shimmer)
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

    private fun showStartItem() {
        _uiData.value = arrayListOf<CoreCell>().apply {
            add(CoreCell.Space)
            add(CoreCell.Start)
        }
    }

    private fun showEmptyItem() {
        _uiData.value = arrayListOf<CoreCell>().apply {
            add(CoreCell.Space)
            add(CoreCell.Empty)
        }
    }

    fun saveSearchItem(item: SearchResult, drink: Drink) {
        uiScope.launch {
            try {
                if (item.title != "") {
                    coreInteractor.addRecentSearch(item)
                    preferencesManager.lastQuery = drink.strDrink
                    _updateHint.value = lastQuery
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun recentClear() {
        uiScope.launch {
            coreInteractor.clearRecentSearch()
            _updateHint.value = resourceManager.getString(R.string.lets_go_find_something_to_drink)
            showStartItem()
        }
    }
}