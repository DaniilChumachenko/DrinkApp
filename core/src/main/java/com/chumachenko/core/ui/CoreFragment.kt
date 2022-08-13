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
import com.chumachenko.core.databinding.FargmentCoreBinding
import com.chumachenko.core.extensions.getCurrentPosition
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
            searchField.searchInput.text.clear()
        }
        searchField.root.setOnClickListener {
            searchField.searchInput.requestFocus()
            showKeyboard(searchField.searchInput)
        }
    }

    private fun initViews() = binding.apply {
        searchField.searchInput.requestFocus()
        showKeyboard(searchField.searchInput)
    }

    private fun initObservers() = viewModel.apply {
        uiData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        playAnimation.observe(viewLifecycleOwner) {
            if (it)
                binding.searchField.searchLottieAnimation.playAnimation()
            else
                binding.searchField.searchLottieAnimation.resumeAnimation()
        }
        errorSearch.observe(viewLifecycleOwner) {
            drinksSnackbar(binding.root)
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
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.scrollUpGroup.isVisible = recyclerView.getCurrentPosition() > 1
                }
            }
        })
    }

    override fun onItemSelected(itemId: String) {

    }

    override fun onIngredientsClick(ingredient: String) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        viewModel.setShimmers()
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text != null) {
            viewModel.search(text.toString())
        }
    }

    override fun afterTextChanged(text: Editable?) {
        binding.searchField.clearSearch.isInvisible = text.toString() == ""
        if (text.toString() == "") {
            viewModel.startItem()
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