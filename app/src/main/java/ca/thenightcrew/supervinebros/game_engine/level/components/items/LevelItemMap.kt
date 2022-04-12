package ca.thenightcrew.supervinebros.game_engine.level.components.items


/**
 * A map representing the left and right objects that will be displayed in the RecyclerView
 * ImageView grid of LevelItems
 */
class LevelItemMap(size: Int) {
    /** @return The list of items belongs to the left column in the LevelItemMap
     */
    val sideLeft: Array<LevelItem?>

    /** @return The list of items belongs to the right column in the LevelItemMap
     */
    val sideRight: Array<LevelItem?>

    /**
     * Method use to put items in the LevelItemMap
     *
     * @param side The column side of the grid the LevelItem belongs
     * @param row The row of the column that LevelItem belongs
     * @param levelItem The level item that will be used to be displayed in the LevelRecyclerView
     */
    fun put(side: String, row: Int, levelItem: LevelItem?) {
        val levelItemArray = if (side === "left") sideLeft else sideRight
        levelItemArray[row] = levelItem
    }

    init {
        sideLeft = arrayOfNulls(size)
        sideRight = arrayOfNulls(size)
    }
}