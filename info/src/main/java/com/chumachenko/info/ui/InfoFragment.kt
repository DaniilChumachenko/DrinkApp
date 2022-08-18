package com.chumachenko.info.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chumachenko.core.common.base.BaseFragment
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.extensions.viewBinding
import com.chumachenko.info.R
import com.chumachenko.info.databinding.FargmentInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : BaseFragment(R.layout.fargment_info), InfoAdapter.InfoClickListener {

    private val viewModel by viewModel<InfoViewModel>()
    private val binding by viewBinding(FargmentInfoBinding::bind)
    lateinit var infoAdapter: InfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        viewModel.drink = requireArguments().getParcelable(ARG_DRINK_ITEM)!!
        viewModel.getDrinkById(viewModel.drink)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initAdapter()
        initListeners()
    }

    private fun initListeners() = binding.apply {
    }

    private fun initAdapter() = binding.infoRecyclerView.apply {
        setHasFixedSize(true)
        itemAnimator = null
        val manager = LinearLayoutManager(context)
        layoutManager = manager
        infoAdapter = InfoAdapter(
            this@InfoFragment
        )
        adapter = infoAdapter
    }

    private fun initObservers() = viewModel.apply {
        uiData.observe(viewLifecycleOwner) {
            infoAdapter.setData(arrayListOf<InfoCell>().apply {
                add(it)
            })
        }
    }

    override fun onIngredientsClick(name: String) {
        viewModel.openSearchByIngredients(name)
    }

    companion object {
        private const val ARG_DRINK_ITEM = "ARG_DRINK_ITEM"

        fun newInstance(drink: Drink): InfoFragment {
            val fragment = InfoFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(ARG_DRINK_ITEM, drink)
            }
            return fragment
        }
    }
}