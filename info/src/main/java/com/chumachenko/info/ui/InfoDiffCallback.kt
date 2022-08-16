package com.chumachenko.info.ui

import androidx.recyclerview.widget.DiffUtil

class InfoDiffCallback(
    private val oldList: List<InfoCell>,
    private val newList: List<InfoCell>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is InfoCell.Item && newItem is InfoCell.Item) {
            oldItem.drink.idDrink == newItem.drink.idDrink
        }else if (oldItem is InfoCell.Shimmer && newItem is InfoCell.Shimmer) {
            true
        } else {
            oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is InfoCell.Item && newItem is InfoCell.Item) {
            return false
        }

        return oldItem == newItem
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }
}