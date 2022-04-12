package ca.thenightcrew.supervinebros.game_engine.level.components

import android.graphics.Bitmap
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItemMap


/**
 * Level segment data is used to populate the recycler view with information for the background and
 * LevelItem resources
 */
class LevelSegment(
    val vineResourceID: Int,
    val backgroundBitmap: Bitmap?,
    val lastBackgroundBitmap: Bitmap?,
    val levelItemMap: LevelItemMap?
)