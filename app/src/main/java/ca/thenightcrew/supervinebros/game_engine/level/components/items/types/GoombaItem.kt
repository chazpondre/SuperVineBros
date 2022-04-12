package ca.thenightcrew.supervinebros.game_engine.level.components.items.types

import ca.thenightcrew.supervinebros.game_engine.level.components.items.types.BadItem
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItem
import ca.thenightcrew.supervinebros.R

class GoombaItem : BadItem(), LevelItem {
    /** The drawable resources used when this item appears  */
    override val resourceIDs: IntArray
        get() = intArrayOf(
            R.drawable.slide_goomba_00001,
            R.drawable.slide_goomba_00002,
            R.drawable.slide_goomba_00003,
            R.drawable.slide_goomba_00004
        )

    /** See [LevelItem.getSize]  */
    override val size: Int
        get() = 70
}