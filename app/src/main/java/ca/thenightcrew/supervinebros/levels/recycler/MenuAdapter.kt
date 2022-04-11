package ca.thenightcrew.supervinebros.levels.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.levels.Level
import ca.thenightcrew.supervinebros.levels.NoDiffer

internal class MenuAdapter : ListAdapter<Level, ViewBinder>(NoDiffer<Level>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBinder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_menu_item, parent, false)
        return ViewBinder(layout)
    }

    override fun onBindViewHolder(binder: ViewBinder, position: Int) {
        binder.bind(getItem(position))
    }
}