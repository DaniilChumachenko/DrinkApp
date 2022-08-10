package com.chumachenko.core.ui

import androidx.recyclerview.widget.DiffUtil

class CoreDiffCallback(
    private val oldList: List<СoreCell>,
    private val newList: List<СoreCell>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is СoreCell.Item && newItem is СoreCell.Item) {
            oldItem.item.idDrink == newItem.item.idDrink
        }else if (oldItem is СoreCell.Skeleton && newItem is СoreCell.Skeleton) {
            true
        } else {
            oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is СoreCell.Item && newItem is СoreCell.Item) {
            return false
        }

        return oldItem == newItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is СoreCell.Item && newItem is СoreCell.Item && oldItem.selectId != newItem.selectId) {
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