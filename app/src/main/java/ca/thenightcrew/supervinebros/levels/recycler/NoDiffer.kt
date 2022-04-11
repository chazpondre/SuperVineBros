package ca.thenightcrew.supervinebros.levels

import androidx.recyclerview.widget.DiffUtil

internal class NoDiffer<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = false
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = false
}