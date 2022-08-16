package com.chumachenko.info.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.chumachenko.core.common.base.BaseFragment
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.extensions.dpToPixel
import com.chumachenko.core.extensions.viewBinding
import com.chumachenko.info.R
import com.chumachenko.info.databinding.FargmentInfoBinding
import com.google.android.flexbox.FlexboxLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoFragment : BaseFragment(R.layout.fargment_info) {

    private val viewModel by viewModel<InfoViewModel>()
    private val binding by viewBinding(FargmentInfoBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        viewModel.drink = requireArguments().getParcelable(ARG_DRINK_ITEM)!!
        viewModel.getDrinkById(viewModel.drink.idDrink)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
//        initAdapter()
        initListeners()
    }

    private fun initListeners() = binding.apply {
    }

    private fun initObservers() = viewModel.apply {
        uiData.observe(viewLifecycleOwner) {
            val drink = it.first
            val ingredients = it.second
            initViews(drink)
            initIngredients(ingredients)
        }
    }

    private fun initViews(drink: Drink) = binding.apply {
        Glide.with(requireContext())
            .load(drink.strDrinkThumb)
            .transform(
                CenterCrop(),
                RoundedCorners(dpToPixel(20, requireContext()).toInt())
            )
            .into(iconInfo)

        titleInfo.text =
            setStartColor(getString(R.string.drink_name), drink.strDrink)
        instructionInfo.text =
            setStartColor(getString(R.string.drink_instruction), drink.strInstructions)
        categoryDrinkInfo.text =
            setStartColor(getString(R.string.drink_category), drink.strCategory)
        alcoholicDrinkInfo.text =
            setStartColor(getString(R.string.drink_alcoholic), drink.strAlcoholic)
        glassDrinkInfo.text =
            setStartColor(getString(R.string.drink_glass), drink.strGlass)
    }

    private fun setStartColor(string: String, strDrink: String): Spannable =
        SpannableString("$string $strDrink").apply {
            setSpan(
                ForegroundColorSpan(requireContext().getColor(com.chumachenko.core.R.color.main_dark)),
                0,
                string.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

    private fun initIngredients(
        ingredients: List<String>
    ) {
        binding.ingredientsLayout.removeAllViewsInLayout()
        for (name in ingredients) {
            val view = TextView(requireContext())
            view.text = name
            view.paint.isUnderlineText = true
            view.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.chumachenko.core.R.color.ingredients_color
                )
            )
            view.typeface = ResourcesCompat.getFont(
                requireContext(),
                com.chumachenko.core.R.font.falling_sky_small
            )
            val params = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            params.topMargin = dpToPixel(3, view.context).toInt()
            params.bottomMargin = 0
            params.marginEnd = dpToPixel(5, view.context).toInt()
            view.setOnClickListener {
//                    listener.onIngredientsClick(view.text.toString())
            }
            binding.ingredientsLayout.addView(view, params)
        }
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