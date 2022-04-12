package ca.thenightcrew.supervinebros.game_engine.level.components.items.types

import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.game_engine.level.components.items.types.BadItem
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItem

class BooItem : BadItem(), LevelItem {
    /** The drawable resources used when this item appears  */
    override val resourceIDs: IntArray
        get() = intArrayOf(
            R.drawable.slide_coin_00000,
            R.drawable.slide_coin_00001,
            R.drawable.slide_coin_00002,
            R.drawable.slide_coin_00003
        )

    /** See [LevelItem.getSize]  */
    override val size: Int
        get() = 0
}