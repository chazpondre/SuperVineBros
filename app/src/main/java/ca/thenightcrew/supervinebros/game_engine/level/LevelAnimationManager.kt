package ca.thenightcrew.supervinebros.game_engine.level

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.animation.AnimationEngine
import ca.thenightcrew.supervinebros.animation.ConstantKeyFrame
import ca.thenightcrew.supervinebros.animation.KeyTime
import ca.thenightcrew.supervinebros.animation.SimpleKeyFrame
import ca.thenightcrew.supervinebros.game_engine.Utils
import ca.thenightcrew.supervinebros.game_engine.level.InteractionUtils.detectInteraction
import ca.thenightcrew.supervinebros.game_engine.level.LevelAnimationUtils.animateItemsOnScreen
import ca.thenightcrew.supervinebros.game_engine.level.LevelAnimationUtils.getLevelItemsInViewHolder
import ca.thenightcrew.supervinebros.game_engine.level.LevelAnimationUtils.getVisibleSegmentIndices
import ca.thenightcrew.supervinebros.game_engine.level.components.LevelRecyclerView
import ca.thenightcrew.supervinebros.game_engine.level.components.LevelSegment


class LevelAnimationManager(private val levelStatusMutableLiveData: MutableLiveData<LevelStatusEnum>) {
    private val PLAY_GAME = "PLAY"
    private val LEVEL_COMPLETE = "COMPLETE"
    private var deltaY = 1
    private val speedLimit = 8
    private var prevY1 = -1
    private var isPaused = false

    /**  The animation keyframe used to make the level scroll, animate level items and help with
     * interaction detections.
     *
     * @param animationEngine
     * @param levelRecyclerView
     * @param linearLayoutManager
     * @param levelSegments
     * @param interactionEventMutableLiveData
     */
    fun onLevelStart(
        animationEngine: AnimationEngine,
        levelRecyclerView: LevelRecyclerView,
        linearLayoutManager: LinearLayoutManager,
        levelSegments: List<LevelSegment>,
        interactionEventMutableLiveData: MutableLiveData<InteractionEvent>
    ) {
        animationEngine.addOnEachAnimationFrame(
            PLAY_GAME,
            object : ConstantKeyFrame() {
                override fun onEachFrame(frameNumber: Int) {
                    if (isPaused) return
                    // Change the recyclerView Scroll
                    levelRecyclerView.scrollBy(0, deltaY)
                    val yPos = levelRecyclerView.computeVerticalScrollOffset()
                    if (!levelCompleted(yPos, animationEngine)) {
                        prevY1 = yPos
                        val recyclerHeight = levelRecyclerView.height.toFloat()
                        if (recyclerHeight == 0f) return
                        handleLevelItemAnimationsAndInteractions(
                            yPos,
                            linearLayoutManager,
                            levelSegments,
                            interactionEventMutableLiveData,
                            recyclerHeight,
                            frameNumber
                        )
                        increaseLevelSpeed(frameNumber)
                    } else {
                        prevY1 = -1
                        deltaY = 1
                    }
                }
            })
    }

    /**
     * Animates any visible [ ] on screen.
     * This is important as not all views need to be drawn to the screen. The recyclerView handles the
     * garbage collection and recycling of repetitive views while this method ensure items within the
     * visible range of the recycler view is animated and interactable.
     *
     * @param yPos the position of the recyclerView scroll
     * @param linearLayoutManager the manager used to layout the recyclerView
     * @param levelSegments the data used to populate the recyclerView
     * @param interactionEventMutableLiveData the liveData use to notify subscribers of interactions
     * @param recyclerHeight the height of the recyclerView used to draw the level
     * @param frameNumber the current frame number used in the animation of the level
     */
    private fun handleLevelItemAnimationsAndInteractions(
        yPos: Int,
        linearLayoutManager: LinearLayoutManager,
        levelSegments: List<LevelSegment>,
        interactionEventMutableLiveData: MutableLiveData<InteractionEvent>,
        recyclerHeight: Float,
        frameNumber: Int
    ) {
        // Get Visible Indices
        val visibleSegmentIndices = getVisibleSegmentIndices(yPos)
        for (i in visibleSegmentIndices.indices) {
            val visibleSegmentIndex = visibleSegmentIndices[i]
            val itemsInViewHolder: List<List<ImageView>>? =
                getLevelItemsInViewHolder(visibleSegmentIndex, linearLayoutManager)
            if (itemsInViewHolder != null) {
                val levelSegment = levelSegments[visibleSegmentIndex]

                // Delegates Item Interaction
                detectInteraction(
                    interactionEventMutableLiveData,
                    yPos,
                    levelSegment,
                    itemsInViewHolder,
                    recyclerHeight,
                    i,
                    speedLimit
                )
                // Animate at 6 frames per second
                if (frameNumber % 10 == 0) animateItemsOnScreen(
                    levelSegment,
                    itemsInViewHolder,
                    0,
                    3
                )
            }
        }
    }

    fun onLevelEnd(animationEngine: AnimationEngine, view: View) {
        val blackBackground = view.findViewById<View>(R.id.black_background)
        val animationLength = 2
        animationEngine.addKeyframe(
            "Fade Background", 3f,
            object : SimpleKeyFrame(KeyTime(0f, animationLength.toFloat()), true, 0) {
                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    val opacity = covertFrameToSeconds(frameNumber) / animationLength
                    blackBackground.alpha = opacity
                }
            })
    }

    private fun increaseLevelSpeed(frameNumber: Int) {
        if (deltaY < getSpeedLimit() && frameNumber % 5 == 0) {
            deltaY++
        }
    }

    private fun getSpeedLimit(): Float {
        return Utils.Measurement.pixelToDP(speedLimit)
    }

    private fun levelCompleted(nextY: Int, animationEngine: AnimationEngine): Boolean {
        if (prevY1 == nextY) {
            animationEngine.removeKeyframe(PLAY_GAME)
            levelStatusMutableLiveData.postValue(LevelStatusEnum.LEVEL_COMPLETE)
            return true
        }
        return false
    }

    fun setPaused(paused: Boolean) {
        isPaused = paused
    }

    fun resetSpeed() {
        deltaY = 5
    }
}
