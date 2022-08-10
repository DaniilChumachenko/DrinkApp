package com.chumachenko.core.ui

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
import com.chumachenko.core.R
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.databinding.ItemCoreBinding
import com.chumachenko.core.databinding.ItemSearchCoreBinding
import com.chumachenko.core.extensions.dpToPixel
import com.google.android.flexbox.FlexboxLayout

class CoreAdapter(
    private val listener: CoreClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val cells = ArrayList<СoreCell>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                ItemViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_core, parent, false)
                )
            }
            VIEW_TYPE_SKELETON -> {
                SkeletonViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_shimmer_core, parent, false)
                )
            }
            VIEW_TYPE_EMPTY -> {
                EmptyViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_empty_core, parent, false)
                )
            }
            VIEW_TYPE_SEARCH -> {
                SearchViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_search_core, parent, false)
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
                (cells[position] as СoreCell.Item).item,
                (cells[position] as СoreCell.Item).ingredients
            )
            is SearchViewHolder -> holder.bind(listener)
        }
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (cells[position]) {
            is СoreCell.Item -> VIEW_TYPE_ITEM
            is СoreCell.Skeleton -> VIEW_TYPE_SKELETON
            is СoreCell.Search -> VIEW_TYPE_SEARCH
            is СoreCell.Empty -> VIEW_TYPE_EMPTY
        }
    }

    fun setData(newCells: List<СoreCell>) {
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
                val diffCallback = CoreDiffCallback(cells, newCells)
                val result = DiffUtil.calculateDiff(diffCallback, false)
                result.dispatchUpdatesTo(this)
                this.cells.clear()
                this.cells.addAll(newCells)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (holder is ItemViewHolder && payloads.firstOrNull() is СoreCell.Item) {
            holder.updateUI((payloads.first() as СoreCell.Item))
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    class SkeletonViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCoreBinding.bind(view)

        fun bind(
            listener: CoreClickListener,
            item: Drink,
            ingredients: List<String>
        ) {
            binding.apply {

                Glide.with(itemView.context)
                    .load(item.strDrinkThumb)
                    .error(R.drawable.bg_shimmer_icon)
                    .transform(
                        CenterCrop(),
                        RoundedCorners(dpToPixel(8, itemView.context).toInt())
                    )
                    .into(drinkImage)

                drinkTitle.text = item.strDrink
                drinkSubTitle.text = item.strInstructions
                displayIngredients(ingredients, listener)
                root.setOnClickListener {

                }
            }
        }

        private fun displayIngredients(ingredients: List<String>, listener: CoreClickListener) {
            binding.ingredientsLayout.removeAllViewsInLayout()
            for (name in ingredients) {
                val view = TextView(itemView.context)
                view.text = name
                view.paint.isUnderlineText = true
                view.setTextColor(ContextCompat.getColor(itemView.context, R.color.ingredients_color))
                view.typeface = ResourcesCompat.getFont(itemView.context, R.font.falling_sky_small)
                val params = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                )
                params.topMargin = dpToPixel(3, view.context).toInt()
                params.bottomMargin = 0
                params.marginEnd = dpToPixel(5, view.context).toInt()
                view.setOnClickListener {
                    listener.onIngredientsClick(view.text.toString())
                }
                binding.ingredientsLayout.addView(view, params)
            }
        }

        fun updateUI(item: СoreCell.Item) {
//            binding.projectStatus.setImageDrawable(
//                ContextCompat.getDrawable(
//                    itemView.context,
//                    if (item.item.id == item.selectId) R.drawable.ic_project_checkbox_on
//                    else R.drawable.ic_project_checkbox_off
//                )
//            )
        }
    }

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemSearchCoreBinding.bind(view)

        fun bind(
            listener: CoreClickListener
        ) {
            binding.apply {
                root.setOnClickListener {
                    listener.onSearchClick()
                }
            }
        }
    }

    interface CoreClickListener {
        fun onItemSelected(itemId: String)
        fun onSearchClick()
        fun onIngredientsClick(ingredient: String)
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_SEARCH = 2
        private const val VIEW_TYPE_SKELETON = 3
        private const val VIEW_TYPE_EMPTY = 4
    }
}