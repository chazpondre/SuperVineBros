package ca.thenightcrew.supervinebros.game_engine.level

import android.widget.ImageView
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItem

/**
 * Represents the Level Items and its associated ImageViews that has interacted with a sprite
 */
class InteractionEvent internal constructor(
    val levelItemLeft: LevelItem?,
    val levelItemRight: LevelItem?,
    imageViewLeft: ImageView,
    imageViewRight: ImageView, eventID: Int
) {
    private val imageViewLeft: ImageView
    private val imageViewRight: ImageView
    private val eventID: Int
    fun getImageViewLeft(): ImageView {
        return imageViewLeft
    }

    fun getImageViewRight(): ImageView {
        return imageViewRight
    }

    init {
        this.imageViewLeft = imageViewLeft
        this.imageViewRight = imageViewRight
        this.eventID = eventID
    }
}
