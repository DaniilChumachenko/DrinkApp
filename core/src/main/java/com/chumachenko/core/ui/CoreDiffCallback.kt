package com.chumachenko.core.ui

import androidx.recyclerview.widget.DiffUtil

class CoreDiffCallback(
    private val oldList: List<CoreCell>,
    private val newList: List<CoreCell>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is CoreCell.Item && newItem is CoreCell.Item) {
            oldItem.item.idDrink == newItem.item.idDrink
        }else if (oldItem is CoreCell.Shimmer && newItem is CoreCell.Shimmer) {
            true
        }else if (oldItem is CoreCell.Start && newItem is CoreCell.Start) {
            true
        } else {
            oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is CoreCell.Item && newItem is CoreCell.Item) {
            return false
        }else if (oldItem is CoreCell.Start && newItem is CoreCell.Start) {
            return false
        }

        return oldItem == newItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is CoreCell.Start && newItem is CoreCell.Start) {
            return newItem
        }
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }
}