package com.chumachenko.info.ui

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.extensions.dpToPixel
import com.chumachenko.info.R
import com.chumachenko.info.databinding.ItemInfoBinding
import com.google.android.flexbox.FlexboxLayout

class InfoAdapter(
    private val listener: InfoClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val cells = ArrayList<InfoCell>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                ItemViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_info, parent, false)
                )
            }
            VIEW_TYPE_SKELETON -> {
                ShimmerViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_shimmer_info, parent, false)
                )
            }
            else -> {
                throw IllegalArgumentException("Unknown viewType $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(
                listener,
                cells[position] as InfoCell.Item
            )
        }
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (cells[position]) {
            is InfoCell.Item -> VIEW_TYPE_ITEM
            is InfoCell.Shimmer -> VIEW_TYPE_SKELETON
        }
    }

    fun setData(newCells: List<InfoCell>) {
        when {
            cells.isEmpty() -> {
                cells.addAll(newCells)
                notifyDataSetChanged()
            }
            newCells.isEmpty() -> {
                cells.clear()
                notifyDataSetChanged()
            }
            else -> {
                val diffCallback = InfoDiffCallback(cells, newCells)
                val result = DiffUtil.calculateDiff(diffCallback, false)
                result.dispatchUpdatesTo(this)
                this.cells.clear()
                this.cells.addAll(newCells)
            }
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInfoBinding.bind(view)

        fun bind(
            listener: InfoClickListener,
            item: InfoCell.Item
        ) {
            initViews(item.drink)
            initIngredients(item.ingredients, listener)
        }

        private fun initViews(drink: Drink) = binding.apply {
            Glide.with(itemView.context)
                .load(drink.strDrinkThumb)
                .transform(
                    CenterCrop(),
                    RoundedCorners(dpToPixel(20, itemView.context).toInt())
                )
                .into(iconInfo)

            titleInfo.text =
                setStartColor(itemView.context.getString(R.string.drink_name), drink.strDrink)
            instructionInfo.text =
                setStartColor(
                    itemView.context.getString(R.string.drink_instruction),
                    drink.strInstructions
                )
            categoryDrinkInfo.text =
                setStartColor(
                    itemView.context.getString(R.string.drink_category),
                    drink.strCategory
                )
            alcoholicDrinkInfo.text =
                setStartColor(
                    itemView.context.getString(R.string.drink_alcoholic),
                    drink.strAlcoholic
                )
            glassDrinkInfo.text =
                setStartColor(itemView.context.getString(R.string.drink_glass), drink.strGlass)
        }

        private fun setStartColor(string: String, strDrink: String): Spannable =
            SpannableString("$string $strDrink").apply {
                setSpan(
                    ForegroundColorSpan(itemView.context.getColor(R.color.main_dark)),
                    0,
                    string.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

        private fun initIngredients(
            ingredients: List<String>,
            listener: InfoClickListener
        ) {
            binding.ingredientsLayout.removeAllViewsInLayout()
            ingredients.forEachIndexed { index, name ->
                val view = TextView(itemView.context)

                when {
                    index != ingredients.lastIndex -> view.text =
                        name.plus(",")
                    else -> view.text = name
                }

                view.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        com.chumachenko.core.R.color.ingredients_color
                    )
                )
                view.typeface = ResourcesCompat.getFont(
                    itemView.context,
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
                    listener.onIngredientsClick(name)
                }
                binding.ingredientsLayout.addView(view, params)
            }
        }
    }

    class ShimmerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface InfoClickListener {
        fun onIngredientsClick(name: String)
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_SKELETON = 2
    }
}