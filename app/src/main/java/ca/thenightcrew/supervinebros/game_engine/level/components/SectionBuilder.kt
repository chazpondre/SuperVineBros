package ca.thenightcrew.supervinebros.game_engine.level.components

import android.content.res.Resources
import android.graphics.Bitmap
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.generateClippedImage
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.generateImage
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.generateInversedClippedImage
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItemFactory.createLevelItem
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItemMap
import ca.thenightcrew.supervinebros.game_engine.level.components.items.types.LevelItemType


/**
 * SectionBuilder is used as a tool for constructing levels in a easy manor. It can repeat
 * background and help paint LevelObjects. It inherits from the ArrayList as segments are essential
 * just a list of data that hold background data.
 */
open class SectionBuilder protected constructor(resources: Resources) :
    ArrayList<LevelSegment>() {
    //  private LevelDesigner levelSegments;
    private val resources: Resources
    private var repeatAmount = 0
    private var vine = 0
    private var currentBackgroundResourceID = 0
    private var background: Bitmap? = null
    private var lastBackground: Bitmap? = null
    private var clippedBackground: Bitmap? = null
    private var objectPainters: ArrayList<LevelItemPainter>? = null
    private val OBJECT_ROWS = 4
    /**
     * This method is used to calculate a users score.
     *
     * @return The longest time it will take a user to complete the level
     */
    /**
     * This method is used to calculate a users score by differing their times to the worst times.
     *
     * @param levelWorstTime The longest time it will take a user to complete the level measured in
     * seconds
     */
    var levelWorstTime = 0

    /** Method used to reinitialize the section builder  */
    private fun initialize() {
        clippedBackground = null
        background = null
        vine = 0
        repeatAmount = 0
        objectPainters = null
    }

    protected fun addVine(vine: Int): SectionBuilder {
        this.vine = vine
        return this
    }

    /**
     * Add objects to each level segment such as enemies and coins. Section Objects should not exceed
     * 12 combined and no single object should exceed 6.
     *
     *
     * // * @param object The object type that will be added to section.
     *
     * @return This Section Builder
     */
    fun addLevelItem(objectType: LevelItemType?, priority: Int): SectionBuilder {
        if (objectPainters == null) { objectPainters = ArrayList() }
        objectPainters!!.add(LevelItemPainter(objectType!!, priority))
        return this
    }

    /**
     * Adds a background to the current segment painter
     *
     * @param resourceID The id of the background to paint
     * @return This Section Builder
     */
    fun addBackground(resourceID: Int): SectionBuilder {
        background = generateImage(resources, resourceID)
        clippedBackground = generateClippedImage(resources, resourceID)
        currentBackgroundResourceID = resourceID
        return this
    }

    /**
     * The amount of time the segment should repeat
     *
     * @param amount The amount of times this segment should repeat
     * @return This Section Builder
     */
    fun repeatBackground(amount: Int): SectionBuilder {
        repeatAmount = amount
        return this
    }

    /**
     * Private Helper that generates a SegmentObject Map from a segment object painter
     *
     * @param index the current index of the segment in the List of segments currently being drawn on
     * the screen
     * @return a new LevelItemMap
     */
    private fun getSegmentObjectMap(index: Int): LevelItemMap? {
        if (objectPainters == null) return null
        val levelItemMap = LevelItemMap(OBJECT_ROWS)
        val rowOffset = OBJECT_ROWS * index
        for (row in 0 until OBJECT_ROWS) for (objectPainter in objectPainters!!) {
            if (objectPainter.shouldPaintRow(rowOffset + row)) {
                val levelItem = createLevelItem(objectPainter.getObjectType())
                levelItemMap.put(objectPainter.randomSide, row, levelItem)
            }
        }
        return levelItemMap
    }

    /** Adds all the SegmentBuilder information using the ArrayList helper methods  */
    fun addToLevel() {
        for (i in 0 until repeatAmount + 1) {
            val levelItemMap = getSegmentObjectMap(i)
            if (i == 0) add(LevelSegment(vine, clippedBackground, lastBackground, levelItemMap))
            else add(LevelSegment(vine, background, null, levelItemMap))
        }

        if (background == null) lastBackground = null else generateLastBackground()
        initialize()
    }

    /** A function that generates a clipped background that makes background look seamless  */
    private fun generateLastBackground() {
        lastBackground = generateInversedClippedImage(resources, currentBackgroundResourceID)
    }


    init {
        initialize()
        this.resources = resources
    }
}
