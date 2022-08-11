package com.chumachenko.search.ui

import androidx.recyclerview.widget.DiffUtil

class SearchDiffCallback(
    private val oldList: List<SearchCell>,
    private val newList: List<SearchCell>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is SearchCell.Item && newItem is SearchCell.Item) {
            oldItem.item.idDrink == newItem.item.idDrink
        }else if (oldItem is SearchCell.Skeleton && newItem is SearchCell.Skeleton) {
            true
        } else {
            oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is SearchCell.Item && newItem is SearchCell.Item) {
            return false
        }

        return oldItem == newItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is SearchCell.Item && newItem is SearchCell.Item && oldItem.selectId != newItem.selectId) {
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