package com.chumachenko.core.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chumachenko.core.R
import com.chumachenko.core.common.base.BaseFragment
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.databinding.FargmentCoreBinding
import com.chumachenko.core.extensions.getCurrentPosition
import com.chumachenko.core.extensions.second
import com.chumachenko.core.extensions.setConstraintStatusBarHeight
import com.chumachenko.core.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoreFragment : BaseFragment(R.layout.fargment_core), CoreAdapter.CoreClickListener,
    TextWatcher {

    private val viewModel by viewModel<CoreViewModel>()
    private val binding by viewBinding(FargmentCoreBinding::bind)
    lateinit var adapter: CoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        viewModel.setupOnCreateMethod()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setConstraintStatusBarHeight(binding.topInset)
        initObservers()
        initAdapter()
        initListeners()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        binding.searchField.searchInput.addTextChangedListener(this)
    }

    private fun initListeners() = binding.apply {
        scrollUpListBg.setOnClickListener {
            drinksRecyclerView.smoothScrollToPosition(0)
            scrollUpGroup.isVisible = false
        }
        searchField.clearSearch.setOnClickListener {
            viewModel.clearSearchOn = true
            searchField.searchInput.text.clear()
        }
        searchField.root.setOnClickListener {
            searchField.searchInput.requestFocus()
            showKeyboard(searchField.searchInput)
        }
    }

    private fun initViews() = binding.apply {
        searchField.searchInput.requestFocus()
        //TODO ПОДУМАТЬ КАК МОЖЕТ ЧЕ В ТЕКСТЕ СДЕЛАТЬ
        searchField.searchInput.hint = viewModel.lastQuery
        showKeyboard(searchField.searchInput)
    }

    private fun initObservers() = viewModel.apply {
        uiData.observe(viewLifecycleOwner) {
            if (it.first() is CoreCell.Empty)
                hideKeyboard(binding.root)
            if (it.second() is CoreCell.Shimmer)
                binding.searchField.searchLottieAnimation.resumeAnimation()
            else
                binding.searchField.searchLottieAnimation.pauseAnimation()
            adapter.setData(it)
        }
        errorSearch.observe(viewLifecycleOwner) {
            viewModel.showEmptyItem()
            drinksSnackbar(binding.root)
        }
        updateHint.observe(viewLifecycleOwner) {
            binding.searchField.searchInput.hint = viewModel.lastQuery
        }
    }

    private fun initAdapter() {
        binding.drinksRecyclerView.let {
            it.setHasFixedSize(true)
            it.itemAnimator = null
            val manager = LinearLayoutManager(context)
            it.layoutManager = manager
            adapter = CoreAdapter(
                this
            )
            it.adapter = adapter
        }
        binding.drinksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy != 0 && keyboardStatus)
                    hideKeyboard(binding.root)
                binding.scrollUpGroup.isVisible = recyclerView.getCurrentPosition() > 1
            }
        })
    }

    override fun onItemClick(item: Drink) {
        viewModel.saveSearchItem(SearchResult(viewModel.searchQuery), item)
    }

    override fun onIngredientsClick(ingredient: String) {

    }

    override fun onRecentClick(ingredient: String) {
        binding.searchField.searchInput.setText(ingredient)
    }

    override fun onRecentClear() {
        viewModel.recentClear()
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (!viewModel.clearSearchOn && text != "") {
            viewModel.setShimmers()
        } else {
            viewModel.clearSearchOn = false
            hideKeyboard(binding.root)
        }
        keyboardStatus = true
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text != null) {
            viewModel.search(text.toString())
        }
    }

    override fun afterTextChanged(text: Editable?) {
        binding.searchField.clearSearch.isInvisible = text.toString() == ""
        if (text.toString() == "") {
            viewModel.setRecentSearchData()
        }
    }

    companion object {

        //        private const val ARG_COMPILATION_ITEM = "ARG_COMPILATION_ITEM"
//        private const val ARG_SELECTED_MEDIA = "ARG_SELECTED_MEDIA"
        fun newInstance(
//    item: CompilationItem, selectedMedia: Image? = null
        ): CoreFragment {
            val fragment = CoreFragment()
            fragment.arguments = Bundle().apply {
//                putParcelable(ARG_SELECTED_MEDIA, selectedMedia)
//                putParcelable(ARG_COMPILATION_ITEM, item)
            }
            return fragment
        }
    }
}