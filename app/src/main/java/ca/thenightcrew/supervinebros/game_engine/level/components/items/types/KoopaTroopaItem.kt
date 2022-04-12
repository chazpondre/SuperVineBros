package ca.thenightcrew.supervinebros.game_engine.level.components.items.types

import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItem


class KoopaTroopaItem() : BadItem(), LevelItem {
    /** The drawable resources used when this item appears  */
    override val resourceIDs: IntArray
        get() = intArrayOf(
            R.drawable.slide_koopa_troopa_00000, R.drawable.slide_koopa_troopa_00001
        )

    /** See [LevelItem.getSize]  */
    override val size: Int
        get() {
            return 70
        }
}
