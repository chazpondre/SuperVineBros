package ca.thenightcrew.supervinebros.game_engine.level

import android.os.Handler
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.levelItemRowCount
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.levelSegmentHeight
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.levelSegmentItemRowHeight
import ca.thenightcrew.supervinebros.game_engine.Utils
import ca.thenightcrew.supervinebros.game_engine.level.components.LevelSegment


object InteractionUtils {
    /**
     * Detects when the sprite can possibly interact with a LevelItem displayed on the screen on a
     * background thread. When detection occurs the live data object will post its value to the main
     * thread
     *
     * @param interactionEventMutableLiveData A live data object that will notify subscribes of an
     * interaction event
     * @param yPos The current Y position in the level recycler view
     * @param segment The Level Segment data used to populate the recyclerView
     * @param segmentImageViews The ImageViews corresponding LevelItems that belong to the Level
     * Segment
     * @param recyclerHeight The height of the recyclerView
     * @param segmentIndex The index representing the segment which visible on screen. For instance
     * two segments may be displayed at once on the screen, 0 corresponds to the top and 1
     * corresponds to the bottom segment
     * @param speedLimit The speed of which the recyclerView is scroll. This helps with accurately
     * detecting segment level items interactions
     */
    fun detectInteraction(
        interactionEventMutableLiveData: MutableLiveData<InteractionEvent>,
        yPos: Int,
        segment: LevelSegment,
        segmentImageViews: List<List<ImageView>>,
        recyclerHeight: Float,
        segmentIndex: Int,
        speedLimit: Int
    ) {
        Utils.Threads.runOnBackgroundThread {
            val segmentHeight = levelSegmentHeight
            val offset = yPos % segmentHeight
            val rowHeight = levelSegmentItemRowHeight
            val centerOfView = recyclerHeight / 2
            val snapShotHeight: Float = Utils.Measurement.pixelToDP(speedLimit)
            val marginValue: Float = Utils.Measurement.pixelToDP(50)
            val y1 = centerOfView - snapShotHeight / 2 + offset
            val y2 = centerOfView + snapShotHeight / 2 + offset

            // If rows fall within the interaction zone then notify observers of interactionEvent.
            for (i in 0 until levelItemRowCount) {
                val itemPositionY = i * rowHeight + segmentIndex * segmentHeight + marginValue
                if (itemPositionY > y1 && itemPositionY < y2) {
                    // InteractionUtils
                    val levelItemLeft = segment.levelItemMap?.sideLeft?.get(i)
                    val levelItemRight = segment.levelItemMap?.sideRight?.get(i)
                    val imageViewLeft: ImageView = segmentImageViews[0][i]
                    val imageViewRight: ImageView = segmentImageViews[0][i]
                    val eventID = (yPos / segmentHeight).toInt()

                    // Post on Main Thread
                    val mainThread = Handler(imageViewLeft.context.mainLooper)
                    if (levelItemLeft != null || levelItemRight != null) mainThread.post {
                        interactionEventMutableLiveData.setValue(
                            InteractionEvent(
                                levelItemLeft,
                                levelItemRight,
                                imageViewLeft,
                                imageViewRight,
                                eventID
                            )
                        )
                    }
                }
            }
        }
    }
}
