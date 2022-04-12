package ca.thenightcrew.supervinebros.animation

private val LOOP_INCOMPLETE = 490
private val LOOP_COMPLETE = 813
private val LOOP_COMPLETE_RENDER_LAST = 543
private val DEFAULT = 50
private val KEYFRAME = 93

/**
 * Keyframes are used to define distinct points in animation. This animation keyframes are special
 * in the sense that they contain two key frames: A default keyframe and a normal keyframe. This is
 * handy for back and forth animations, such as sprite animations. If you need smooth keyframes make
 * sure to set tweening to true
 */
abstract class KeyFrame(
    val keyTimes: List<KeyTime>,
    private val tweening: Boolean,
    protected var loopTimes: Int = -1
) {
    var timelineName: String? = null

    private var lastRenderedFrameType = -1
    private var lastRenderedFrame = -1
    private var firstFrame = -1

    constructor(keyTime: KeyTime, tweening: Boolean) :
            this(listOf(keyTime), tweening)

    constructor(keyTime: KeyTime, tweening: Boolean, loopTimes: Int) :
            this(listOf(keyTime), tweening, loopTimes)

    fun requestAnimationFrame(frameType: Int, totalFramesCalled: Int): Boolean {
        lastRenderedFrame = totalFramesCalled
        if (tweening) return true
        if (frameType == KEYFRAME && lastRenderedFrameType != KEYFRAME
            || frameType == DEFAULT && lastRenderedFrameType != DEFAULT
        ) {
            lastRenderedFrameType = frameType
            return true
        }

        return false
    }

    fun getCurrentFrame(totalFramesCalled: Int, timelineLength: Int): Int {
        if (firstFrame == -1) {
            firstFrame = totalFramesCalled
        }
        return (totalFramesCalled - firstFrame) % timelineLength
    }

    /**
     * Helps the animation engine determine whether the loop has ended and whether the loop has ended
     * with the last frame called. This ensure clients that the last frame is always called.
     *
     * @param totalFramesCalled The total frames rendered independent of all timelines
     * @param timelineDurationInFrames The duration of the current timeline that the keyframe exists
     * @return The state of whether the loop has ended with last frame called
     */
    fun getLoopState(totalFramesCalled: Int, timelineDurationInFrames: Int): Int {
        if (timelineDurationInFrames == 0 || loopTimes < 0) return LOOP_INCOMPLETE
        // firstFrame KeyFrame was drawn + 1 full timeline length and additional loops of the timeline
        val finalFrame = firstFrame + (loopTimes + 1) * timelineDurationInFrames
        //    return totalFramesCalled < finalFrame;
        val loopCount = (totalFramesCalled - firstFrame) / timelineDurationInFrames
        return if (loopCount > loopTimes)
            if (lastRenderedFrame >= finalFrame) LOOP_COMPLETE else LOOP_COMPLETE_RENDER_LAST
        else LOOP_INCOMPLETE
    }

    /**
     * This type of keyframe represents the default keyframe in this timeline.
     *
     * @param frameNumber The current frame number in the timeline
     * @param totalFramesCalled The total frames rendered independent of all timelines
     */
    abstract fun onDefaultFrame(frameNumber: Int, totalFramesCalled: Int)

    protected fun covertFrameToSeconds(frameNumber: Int): Float {
        return frameNumber.toFloat() / 60
    }

    abstract fun onKeyframe(frameNumber: Int, totalFramesCalled: Int)

    companion object {
        const val LOOP_INCOMPLETE = 490
        const val LOOP_COMPLETE = 813
        const val LOOP_COMPLETE_RENDER_LAST = 543
        const val DEFAULT = 50
        const val KEYFRAME = 93
    }
}

