package ca.thenightcrew.supervinebros.animation

abstract class SimpleKeyFrame(keyTimes: KeyTime, tweening: Boolean) :
    KeyFrame(keyTimes, tweening) {
    override fun onDefaultFrame(frameNumber: Int, totalFramesCalled: Int) {}
}
