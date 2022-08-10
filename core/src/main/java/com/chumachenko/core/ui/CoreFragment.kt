package com.chumachenko.core.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chumachenko.core.R
import com.chumachenko.core.common.InsetHolder
import com.chumachenko.core.common.base.BaseFragment
import com.chumachenko.core.databinding.FargmentCoreBinding
import com.chumachenko.core.extensions.setConstraintStatusBarHeight
import com.chumachenko.core.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoreFragment : BaseFragment(R.layout.fargment_core), CoreAdapter.CoreClickListener {

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
    }

    private fun initObservers() = viewModel.apply {
        uiData.observe(viewLifecycleOwner) {
            adapter.setData(it)
            binding.updateSwipe.isRefreshing = false
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

        binding.updateSwipe.setOnRefreshListener {
            viewModel.getData()
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

    override fun onItemSelected(itemId: String) {

    }

    override fun onSearchClick() {
    }

    override fun onIngredientsClick(ingredient: String) {

    }

}