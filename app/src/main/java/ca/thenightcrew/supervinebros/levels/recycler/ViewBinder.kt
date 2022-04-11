package ca.thenightcrew.supervinebros.levels.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.fragments.LevelSelectorFragmentDirections
import ca.thenightcrew.supervinebros.levels.Level

internal class ViewBinder(item: View) : RecyclerView.ViewHolder(item) {
    fun bind(level: Level) {
        val image = itemView.findViewById<ImageView>(R.id.menu_item_image)
        val text = itemView.findViewById<TextView>(R.id.menu_item_text)

        text.text = level.title
        image.setImageResource(level.resourceId)
        image.setOnClickListener {
            val transition = LevelSelectorFragmentDirections.actionMenuFragmentToGameFragment(level.levelIndex)
            itemView.findNavController().navigate(transition)
        }
    }
}