package ca.thenightcrew.supervinebros.game_engine.level

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.getLevelItemViews
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.leftIDs
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.levelSegmentHeight
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.rightIDs
import ca.thenightcrew.supervinebros.game_engine.level.components.LevelSegment


object LevelAnimationUtils {
    /**
     * Gets all LevelItems currently display on the screen sorted by left and right
     *
     * @param layoutManager The linear layout manager of the level recycler view
     * @param segmentIndex A list of indices in the level recyclerView that are visible
     * @return A left and right list of LevelItems currently displayed on the screen. Returns null if
     * the view holder does not exist
     */
    fun getLevelItemsInViewHolder(
        segmentIndex: Int, layoutManager: LinearLayoutManager
    ): List<MutableList<ImageView>>? {
        val itemsDisplayedOnScreen: MutableList<MutableList<ImageView>> = ArrayList()

        // Add Left and Right List
        itemsDisplayedOnScreen.add(ArrayList<ImageView>())
        itemsDisplayedOnScreen.add(ArrayList<ImageView>())

        // For All Visible ViewHolder, get items and add to list itemsDisplayedOnScreen
        val viewHolder: View? = layoutManager.findViewByPosition(segmentIndex)
        if (viewHolder != null) {
            itemsDisplayedOnScreen[0].addAll(getLevelItemViews(leftIDs, viewHolder))
            itemsDisplayedOnScreen[1].addAll(getLevelItemViews(rightIDs, viewHolder))
        } else return null
        return itemsDisplayedOnScreen
    }

    /**
     * Gets a List of Indices in the RecyclerView that is currently displayed on screen
     *
     * @param yPos The current Y position in the level recycler view
     * @return A list of indices in the level recyclerView that are visible
     */
    fun getVisibleSegmentIndices(yPos: Int): List<Int> {
        val visibleIndices: ArrayList<Int> = ArrayList()

        // Get Second Y coordinate
        val visibilityBufferHeight = levelSegmentHeight
        val y2pos = yPos + visibilityBufferHeight - 1

        // Get Current Visible Indices of ViewHolders and LevelData
        visibleIndices.add(Math.floor((yPos / visibilityBufferHeight).toDouble()).toInt())
        visibleIndices.add(Math.floor((y2pos / visibilityBufferHeight).toDouble()).toInt())
        return visibleIndices
    }

    /**
     * A helper method that helps animate Level Items in recyclerView based on the range of rows
     * provided
     *
     * @param segment The Level Segment data used to populate the recyclerView
     * @param segmentImageViews The image views associated to each level segment
     * @param firstItemRow The first visible row of level items on screen that needs animation
     * @param lastItemRow The last visible row of level items on screen that needs animation
     */
    fun animateItemsOnScreen(
        segment: LevelSegment?,
        segmentImageViews: List<List<ImageView>>,
        firstItemRow: Int,
        lastItemRow: Int
    ) {
        if (segment == null || segment.levelItemMap == null) return
        val itemsLeft: List<ImageView> = segmentImageViews[0]
        val itemsRight: List<ImageView> = segmentImageViews[1]
        val levelItemMap = segment.levelItemMap
        val leftLevelItemsData = levelItemMap.sideLeft
        val rightLevelItemsData = levelItemMap.sideRight
        for (row in firstItemRow..lastItemRow) {
            if (leftLevelItemsData[row] != null) itemsLeft[row].setImageResource(
                leftLevelItemsData[row]!!.nextResourceID
            ) else itemsLeft[row].setImageBitmap(null)
            if (rightLevelItemsData[row] != null) itemsRight[row].setImageResource(
                rightLevelItemsData[row]!!.nextResourceID
            ) else itemsRight[row].setImageBitmap(null)
        }
    }
}
