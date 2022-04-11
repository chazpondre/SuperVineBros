package ca.thenightcrew.supervinebros.animation

/**
 * This type keyframe should be used if you require a keyframe on every frame in an animation. You
 * do not need to provide timing as this is implied
 */
abstract class ConstantKeyFrame : SimpleKeyFrame(KeyTime(0f, 0f), true) {
    override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) =
        onEachFrame(totalFramesCalled)

    abstract fun onEachFrame(frameNumber: Int)
}

fun onEachFrame(action: (frameNumber: Int) -> Unit): SimpleKeyFrame =
    object : SimpleKeyFrame(KeyTime(0f, 0f), true) {
        override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) =
            onEach(totalFramesCalled)

        fun onEach(frameNumber: Int) {
            action(frameNumber)
        }
    }
