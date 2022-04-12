package ca.thenightcrew.supervinebros.game_engine.level.components.items.types

/** Every Item in the recycler view can be animated  */
abstract class LevelItemAnimator {
    private var nextResource = 0
    private var finalAnimation = false
    abstract val resourceIDs: IntArray

    /**
     * The AnimationEngine will call this function when the item is currently displayed on the screen
     * and updates the views accordingly
     *
     *
     * If the finalAnimation flag is checked the item will not loop its animation
     *
     * @return The resource id of the next frame in animation
     */
    val nextResourceID: Int
        get() {
            nextResource =
                if (!finalAnimation) if (nextResource + 1 < resourceIDs.size) nextResource + 1 else 0 else if (nextResource + 1 < resourceIDs.size) nextResource + 1 else nextResource
            return resourceIDs[nextResource]
        }

    /**
     * Sets the finalAnimation flag to true. When the animator gets new frames it will stop at the
     * last frame
     */
    fun setFinalAnimation() {
        nextResource = -1
        finalAnimation = true
    }
}
