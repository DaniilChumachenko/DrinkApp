package com.chumachenko.search.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chumachenko.core.common.base.BaseFragment
import com.chumachenko.core.extensions.setConstraintStatusBarHeight
import com.chumachenko.core.extensions.viewBinding
import com.chumachenko.search.R
import com.chumachenko.search.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(R.layout.fragment_search), SearchAdapter.CoreClickListener {

    private val viewModel by viewModel<SearchViewModel>()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private lateinit var adapter: SearchAdapter

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
    }

    private fun initListeners() = binding.apply {
        back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun initObservers() = viewModel.apply {
        uiData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun initAdapter() {
        binding.drinksRecyclerView.let {
            it.setHasFixedSize(true)
            it.itemAnimator = null
            val manager = LinearLayoutManager(context)
            it.layoutManager = manager
            adapter = SearchAdapter(
                this
            )
            it.adapter = adapter
        }
    }

    companion object {

        //        private const val ARG_COMPILATION_ITEM = "ARG_COMPILATION_ITEM"
//        private const val ARG_SELECTED_MEDIA = "ARG_SELECTED_MEDIA"
        fun newInstance(
//    item: CompilationItem, selectedMedia: Image? = null
        ): SearchFragment {
            val fragment = SearchFragment()
            fragment.arguments = Bundle().apply {
//                putParcelable(ARG_SELECTED_MEDIA, selectedMedia)
//                putParcelable(ARG_COMPILATION_ITEM, item)
            }
            return fragment
        }
    }

    override fun onItemSelected(itemId: String) {

    }

    override fun onSearchClick() {
    }

    override fun onIngredientsClick(ingredient: String) {

    }

}