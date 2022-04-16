package ca.thenightcrew.supervinebros.levels.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.fragments.LevelSelectorFragmentDirections
import ca.thenightcrew.supervinebros.game_engine.AppInfo
import ca.thenightcrew.supervinebros.levels.Level

internal class ViewBinder(item: View) : RecyclerView.ViewHolder(item) {
    var index: Int = -1
    fun bind(level: Level) {
        index = this.layoutPosition
        val image = itemView.findViewById<ImageView>(R.id.menu_item_image)
        val text = itemView.findViewById<TextView>(R.id.menu_item_text)
        val lock = itemView.findViewById<ImageView>(R.id.levelLock)

        text.text = level.title
        image.setImageResource(level.resourceId)


        if (index <= AppInfo.player?.levelsUnlocked!!) {
            image.backgroundTintList = null
            image.setOnClickListener {
                val transition =
                    LevelSelectorFragmentDirections.actionMenuFragmentToGameFragment(level.levelIndex)
                itemView.findNavController().navigate(transition)
            }
        } else {
            image.setColorFilter(0x77000000)
            lock.visibility = View.VISIBLE
        }
    }
}