package ca.thenightcrew.supervinebros.levels

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.thenightcrew.supervinebros.R
import com.google.android.material.snackbar.Snackbar

internal class ViewBinder(item: View) : RecyclerView.ViewHolder(item) {
    fun bind(level: Level) {
        val image = itemView.findViewById<ImageView>(R.id.menu_item_image)
        val text = itemView.findViewById<TextView>(R.id.menu_item_text)

        text.text = level.title
        image.setImageResource(level.resourceId)
        image.setOnClickListener {
            Snackbar.make(itemView, "MenuItem", Snackbar.LENGTH_SHORT).show()
        }
    }
}