package ca.thenightcrew.supervinebros.game_engine.level.components

import ca.thenightcrew.supervinebros.game_engine.level.components.items.types.LevelItemType

/**
 * LevelItemPainter helps paint items on to each screen using a system a priority level. If the item
 * should be painted on the screen in every possible block then the level should be set to 1, Set to
 * 2 for every other etc.
 */
internal class LevelItemPainter(objectType: LevelItemType, priority: Int) {
    private val objectType: LevelItemType
    private val priority: Int
    private val offset: Int
    fun getObjectType(): LevelItemType {
        return objectType
    }

    /**
     * Calculates whether an item should exist on the screen by a given index
     *
     * @param index The index represent the row of which an item can exist in an item grid
     * @return Whether or not an item should be painted into the grid
     */
    fun shouldPaintRow(index: Int): Boolean {
        return (index + offset) % priority == 0
    }

    /**
     * Items can only exist on side per row this helper helps generate the side of the two columns a
     * item should exists
     *
     * @return A randomly generated side of either left or right
     */
    val randomSide: String
        get() = if (Math.random() < 0.5) "left" else "right"

    init {
        this.objectType = objectType
        this.priority = priority
        offset = (priority * Math.random()).toInt()
    }
}