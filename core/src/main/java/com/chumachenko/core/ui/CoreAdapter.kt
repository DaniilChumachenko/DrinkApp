package com.chumachenko.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.chumachenko.core.R
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.databinding.ItemCoreBinding
import com.chumachenko.core.databinding.ItemRecentCoreBinding
import com.chumachenko.core.databinding.ItemStartCoreBinding
import com.chumachenko.core.extensions.dpToPixel
import com.google.android.flexbox.FlexboxLayout

class CoreAdapter(
    private val listener: CoreClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val cells = ArrayList<CoreCell>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                ItemViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_core, parent, false)
                )
            }
            VIEW_TYPE_SHIMMER -> {
                ShimmerViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_shimmer_core, parent, false)
                )
            }
            VIEW_TYPE_START -> {
                StartViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_start_core, parent, false)
                )
            }
            VIEW_TYPE_EMPTY -> {
                EmptyViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_empty_core, parent, false)
                )
            }
            VIEW_TYPE_SPACE -> {
                SpaceViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_space_core, parent, false)
                )
            }
            VIEW_TYPE_RECENT -> {
                RecentViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_recent_core, parent, false)
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
                (cells[position] as CoreCell.Item).item
            )
            is RecentViewHolder -> holder.bind(
                listener,
                (cells[position] as CoreCell.Recent).item
            )
            is StartViewHolder -> holder.bind(
                cells
            )
        }
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (cells[position]) {
            is CoreCell.Item -> VIEW_TYPE_ITEM
            is CoreCell.Shimmer -> VIEW_TYPE_SHIMMER
            is CoreCell.Space -> VIEW_TYPE_SPACE
            is CoreCell.Start -> VIEW_TYPE_START
            is CoreCell.Empty -> VIEW_TYPE_EMPTY
            is CoreCell.Recent -> VIEW_TYPE_RECENT
        }
    }

    fun setData(newCells: List<CoreCell>) {
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
        if (holder is StartViewHolder && payloads.firstOrNull() is CoreCell.Start) {
            holder.showArrow(cells)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCoreBinding.bind(view)

        fun bind(
            listener: CoreClickListener,
            item: Drink
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
                drinkSubTitle.text = if (item.strInstructions == "") {
                    itemView.context.getString(R.string.tap_to_see_more)
                } else {
                    item.strInstructions
                }
                root.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
        }
    }

    class RecentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRecentCoreBinding.bind(view)

        fun bind(
            listener: CoreClickListener,
            recent: List<SearchResult>
        ) {
            binding.apply {
                recentLayout.removeAllViewsInLayout()
                recent.forEachIndexed { index, drink ->
                    val view = TextView(itemView.context)

                    when {
                        index != recent.lastIndex && index != 0 -> view.text =
                            drink.title.plus(",")
                        else -> view.text = drink.title
                    }

                    view.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.main_gray
                        )
                    )
                    view.typeface =
                        ResourcesCompat.getFont(itemView.context, R.font.falling_sky_small)
                    val params = FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.topMargin = dpToPixel(3, view.context).toInt()
                    params.bottomMargin = 0
                    params.marginEnd = dpToPixel(5, view.context).toInt()

                    view.setOnClickListener {
                        listener.onRecentClick(drink.title)
                    }
                    recentLayout.addView(view, params)
                }
                clearRecent.setOnClickListener {
                    listener.onRecentClear()
                }
            }
        }
    }

    class ShimmerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class StartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStartCoreBinding.bind(view)

        fun bind(cells: ArrayList<CoreCell>) {
            showArrow(cells)
        }

        fun showArrow(cells: ArrayList<CoreCell>) {
            var haveRecent = false
            cells.forEach {
                if (it is CoreCell.Recent)
                    haveRecent = true
            }
            if (!haveRecent) {
                binding.arrowToSearch.isVisible = true
                binding.arrowToSearch.playAnimation()
            } else {
                binding.arrowToSearch.cancelAnimation()
                binding.arrowToSearch.isVisible = false
            }

            binding.startCoreLottie.playAnimation()
        }
    }

    class SpaceViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface CoreClickListener {
        fun onItemClick(drink: Drink)
        fun onRecentClick(ingredient: String)
        fun onRecentClear()
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_SPACE = 2
        private const val VIEW_TYPE_SHIMMER = 3
        private const val VIEW_TYPE_START = 4
        private const val VIEW_TYPE_EMPTY = 5
        private const val VIEW_TYPE_RECENT = 6
    }
}