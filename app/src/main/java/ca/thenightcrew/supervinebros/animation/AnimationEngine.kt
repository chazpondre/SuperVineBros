package ca.thenightcrew.supervinebros.animation

import android.animation.ValueAnimator
import android.util.SparseArray
import android.view.animation.LinearInterpolator

/**
 * Author: Che Thomas
 * Copyright 2022 Che Thomas
 */

private const val ON_EVERY_FRAME = 464

/**
 * The AnimationEngine is a class that can help make android animation easier by focusing strictly
 * on keyframe programming. Once AnimationEngine is instantiated an ValueAnimator looper is
 * automatically started
 */
class AnimationEngine constructor(private var frameRate: Int = 60) {

    private val animator: ValueAnimator = ValueAnimator.ofInt(0, frameRate)
    private val nameToKeyframeMapIndices: MutableMap<String, KeyFrameMapMeta> = HashMap()
    private val timelineKeyFrameSparseArray = SparseArray<SparseArray<KeyFrame>>()

    /**
     * Initiates the main value animator listener that will be used for all animation
     * callbacks
     */
    init {
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        val CYCLE_SPEED = 1000L
        animator
            .setDuration(CYCLE_SPEED)
            .addUpdateListener { valueAnimator -> // Check if valueAnimator ready
                if (valueAnimator.currentPlayTime != 0L) {
                    handleAnimationUpdate(valueAnimator)
                }
            }
        animator.start()
    }

    /**
     * This method will add this keyframe to every single frame in animation
     *
     * @param name Name of the keyframe. e.g. "sprite hands"
     * @param keyFrame The keyframe object that can be value animated.
     */
    fun addOnEachAnimationFrame(name: String, keyFrame: ConstantKeyFrame) {
        addKeyframe(name, ON_EVERY_FRAME.toFloat(), keyFrame)
    }

    /**
     * This method is used to stop the animation engine when it is no longer need. In order to use the
     * animation engine again you will need to create a new instance
     */
    fun kill() {
        animator.cancel()
        timelineKeyFrameSparseArray.clear()
        nameToKeyframeMapIndices.clear()
    }

    /**
     * This method is called on each new frame of the animation (60fps or given frameRate). Its main
     * task is to get all key frames in all timelines and send each keyframe to be requested for
     * animation
     *
     * @see {@link KeyFrame}
     *
     * @param valueAnimator
     */
    private fun handleAnimationUpdate(valueAnimator: ValueAnimator) {
        // This value is between 0-60(or given frameRate) in any give second since valueAnimator started
        val currentFrameInLooper = valueAnimator.animatedValue as Int

        // The total amount of frames called since the start of the animation
        val secondsElapsed = (valueAnimator.currentPlayTime / 1000).toInt()
        val totalFramesCalled = currentFrameInLooper + secondsElapsed * frameRate

        // For each KeyFrame in each timeline, check if keyframe needs to render
        for (i in 0 until timelineKeyFrameSparseArray.size()) {
            val timelineLength = timelineKeyFrameSparseArray.keyAt(i)
            // int currentFrameInTimeline = totalFramesCalled % timelineLength;

            // Request all keyframes in timeline to be rendered
            val keyFramesInTimeline = timelineKeyFrameSparseArray[timelineLength]
            val keyFrameRemoveList: ArrayList<String?> = ArrayList()
            for (index in 0 until keyFramesInTimeline!!.size()) {
                val key = keyFramesInTimeline.keyAt(index)
                val keyFrame = keyFramesInTimeline[key]
                val currentFrameInTimeline =
                    keyFrame.getCurrentFrame(totalFramesCalled, timelineLength)

                // Check if frame has ended
                val loopState = keyFrame.getLoopState(totalFramesCalled, timelineLength)
                if (loopState != KeyFrame.LOOP_INCOMPLETE) {
                    if (loopState == KeyFrame.LOOP_COMPLETE_RENDER_LAST) requestFrameRender(
                        keyFrame,
                        timelineLength,
                        totalFramesCalled,
                        true
                    )
                    keyFrameRemoveList.add(keyFrame.timelineName)
                } else requestFrameRender(
                    keyFrame,
                    currentFrameInTimeline,
                    totalFramesCalled,
                    false
                )
            }
            for (keyFrameName in keyFrameRemoveList) keyFrameName?.let { removeKeyframe(it) }
        }
    }

    /**
     * Check is keyframes or default frames need to painted on screen. If true the keyframe listener
     * methods onDefaultFrame and onKeyFrame will be called
     *
     * @param keyFrame The specific keyframe to could be rendered
     * @param currentFrameInTimeline the current frame in the timeline where the keyframe belongs
     * @param totalFramesCalled the total frames called in AnimationEngine
     */
    private fun requestFrameRender(
        keyFrame: KeyFrame, currentFrameInTimeline: Int, totalFramesCalled: Int, isForced: Boolean
    ) {
        for (keyTime in keyFrame.keyTimes) {
            val startTime = (keyTime.start * frameRate).toInt()
            val stopTime = (keyTime.stop * frameRate).toInt()

            // Check if current frame in timeline is within keyframe or default frame
            if (startTime <= currentFrameInTimeline && currentFrameInTimeline <= stopTime) {
                if (isForced || keyFrame.requestAnimationFrame(
                        KeyFrame.KEYFRAME,
                        totalFramesCalled
                    )
                ) {
                    keyFrame.onKeyframe(currentFrameInTimeline, totalFramesCalled)
                }
                return
            }
        }
        if (isForced || keyFrame.requestAnimationFrame(
                KeyFrame.DEFAULT,
                totalFramesCalled
            )
        ) keyFrame.onDefaultFrame(currentFrameInTimeline, totalFramesCalled)
    }

    /**
     * Adds keyframes to the AnimationEngine.
     *
     * @param name Name of the keyframe. e.g. "sprite hands"
     * @param timelineDuration The duration in seconds of the timeline your keyframe belongs
     * @param keyFrame The keyframe object that can be value animated.
     */
    fun addKeyframe(
        name: String, timelineDuration: Float, keyFrame: KeyFrame
    ) {
        // If keyframe name already exist, the new keyframe will not be added.
        if (nameToKeyframeMapIndices[name] != null) return
        val timelineDurationInFrames: Int
        keyFrame.timelineName = name
        timelineDurationInFrames = if (timelineDuration == ON_EVERY_FRAME.toFloat()) {
            1
        } else {
            (timelineDuration * frameRate).toInt()
        }
        if (timelineKeyFrameSparseArray[timelineDurationInFrames] == null) timelineKeyFrameSparseArray.put(
            timelineDurationInFrames,
            SparseArray()
        )

        // Null-Check in the case where sparse array has an insertion error
        val keyframesInTimeline = timelineKeyFrameSparseArray[timelineDurationInFrames]
        if (keyframesInTimeline != null) {
            val index = keyframesInTimeline.size() + 1
            keyframesInTimeline.put(index, keyFrame)
            val keyFrameMapMeta = KeyFrameMapMeta(timelineDurationInFrames, index)
            nameToKeyframeMapIndices[name] = keyFrameMapMeta
        }
    }

    /**
     * Removes keyframes from the AnimationEngine by the name it was added
     *
     * @param name
     */
    fun removeKeyframe(name: String) {
        val keyFrameMapMeta = nameToKeyframeMapIndices[name]
        if (keyFrameMapMeta != null) {
            val animationList = timelineKeyFrameSparseArray[keyFrameMapMeta.mapKey]
            animationList!!.remove(keyFrameMapMeta.listIndex)
            nameToKeyframeMapIndices.remove(name)
        }
    }

    fun getFrameRate(): Int {
        return frameRate
    }

    /**
     * A helper class that helps manage adding, removing and calling the keyframes in the animation
     * engine.
     */
    private inner class KeyFrameMapMeta constructor(val mapKey: Int, val listIndex: Int)


}
